package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Door;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TryUnlockActionTest {

    private GameState newState(Room startingRoom) {
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(startingRoom));
    }

    @Test
    void unlocksWithMatchingKeyInInventory() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        Door door = new Door("chiave-ufficio");
        corridoio.addExit("est", ufficio, door);

        GameState state = newState(corridoio);
        state.addItem(new Key("chiave-ufficio", "Chiave", "...", "chiave-ufficio"));

        new TryUnlockAction("est").execute(state);

        assertTrue(door.isOpen());
    }

    @Test
    void doesNotUnlockWithoutMatchingKey() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        Door door = new Door("chiave-ufficio");
        corridoio.addExit("est", ufficio, door);

        new TryUnlockAction("est").execute(newState(corridoio));

        assertFalse(door.isOpen());
    }

    @Test
    void alreadyOpenExitReturnsWithoutError() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        Door door = new Door("chiave-ufficio");
        corridoio.addExit("est", ufficio, door);
        door.tryResolve("chiave-ufficio");

        String result = new TryUnlockAction("est").execute(newState(corridoio));

        assertEquals("Il passaggio è già aperto.", result);
    }
}