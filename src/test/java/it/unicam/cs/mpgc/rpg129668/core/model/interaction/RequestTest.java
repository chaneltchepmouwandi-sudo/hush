package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestTest {

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
    }

    private Item peluche() {
        return new Item() {
            @Override
            public String getId() {
                return "peluche";
            }

            @Override
            public String getName() {
                return "Peluche";
            }

            @Override
            public String getDescription() {
                return "Un peluche consumato dal tempo.";
            }
        };
    }

    private Request nadiaRequest() {
        return new Request(
                "nadia-oggetto",
                new HasItemCondition("peluche"),
                List.of(
                        new TakeItemReward("peluche"),
                        new TrustReward("nadia", 2),
                        new KnowledgeReward("sa-infermiera-ala-nord")
                )
        );
    }

    @Test
    void cannotBeFulfilledWithoutTheItem() {
        assertFalse(nadiaRequest().canBeFulfilled(newState()));
    }

    @Test
    void fulfillingGrantsAllRewards() {
        GameState state = newState();
        state.addItem(peluche());

        boolean fulfilled = nadiaRequest().tryFulfill(state);

        assertTrue(fulfilled);
        assertFalse(state.hasItem("peluche"));
        assertEquals(2, state.getTrust("nadia"));
        assertTrue(state.hasKnowledge("sa-infermiera-ala-nord"));
    }

    @Test
    void requestCannotBeFulfilledTwice() {
        GameState state = newState();
        state.addItem(peluche());
        Request request = nadiaRequest();

        request.tryFulfill(state);
        state.addItem(peluche());
        boolean secondAttempt = request.tryFulfill(state);

        assertFalse(secondAttempt);
        assertEquals(2, state.getTrust("nadia")); // non raddoppiata
    }
}