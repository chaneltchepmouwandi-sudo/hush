package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PickUpActionTest {

    @Test
    void pickingUpItemMovesItToInventory() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        state.placeItem(stanza, new GenericItem("peluche", "Peluche", "..."));

        new PickUpAction("peluche").execute(state);

        assertTrue(state.hasItem("peluche"));
        assertTrue(state.getItemsInRoom(stanza).isEmpty());
    }

    @Test
    void pickingUpMissingItemDoesNothing() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));

        new PickUpAction("non-esiste").execute(state);

        assertFalse(state.hasItem("non-esiste"));
    }
}