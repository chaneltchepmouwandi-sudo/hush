package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.HasItemCondition;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.TrustReward;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TalkActionTest {

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
    }

    private Item peluche() {
        return new Item() {
            @Override public String getId() { return "peluche"; }
            @Override public String getName() { return "Peluche"; }
            @Override public String getDescription() { return "..."; }
        };
    }

    @Test
    void talkingWithoutRequestShowsDescription() {
        Npc elio = new Npc("elio", "Elio", "Un bambino silenzioso.");

        String result = new TalkAction(elio).execute(newState());

        assertEquals("Elio: Un bambino silenzioso.", result);
    }

    @Test
    void talkingWithoutRequiredItemDoesNotGrantReward() {
        GameState state = newState();
        Npc nadia = new Npc("nadia", "Nadia", "...");
        Request request = new Request("nadia-oggetto", new HasItemCondition("peluche"),
                List.of(new TrustReward("nadia", 2)));

        new TalkAction(nadia, request).execute(state);

        assertEquals(0, state.getTrust("nadia"));
    }

    @Test
    void talkingWithRequiredItemGrantsReward() {
        GameState state = newState();
        state.addItem(peluche());
        Npc nadia = new Npc("nadia", "Nadia", "...");
        Request request = new Request("nadia-oggetto", new HasItemCondition("peluche"),
                List.of(new TrustReward("nadia", 2)));

        new TalkAction(nadia, request).execute(state);

        assertEquals(2, state.getTrust("nadia"));
    }
}