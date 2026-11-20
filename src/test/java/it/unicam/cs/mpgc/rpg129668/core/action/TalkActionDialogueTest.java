package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Dialogue;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.HasItemCondition;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.TrustReward;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TalkActionDialogueTest {

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
    }

    @Test
    void usesTheRequestOwnLines() {
        Npc nadia = new Npc("nadia", "Nadia", "...");
        Request request = new Request("r", new HasItemCondition("peluche"),
                List.of(new TrustReward("nadia", 1)), new Dialogue("attesa", "ok", "fatto"));
        GameState state = newState();

        assertEquals("Nadia: attesa", new TalkAction(nadia, request).execute(state));

        state.addItem(new GenericItem("peluche", "Peluche", "..."));
        assertEquals("Nadia: ok", new TalkAction(nadia, request).execute(state));
        assertEquals("Nadia: fatto", new TalkAction(nadia, request).execute(state));
    }

    @Test
    void fallsBackToDefaultLinesWhenNoneAreGiven() {
        Request request = new Request("r", new HasItemCondition("peluche"), List.of());

        assertEquals(Dialogue.DEFAULT, request.getDialogue());
    }
}