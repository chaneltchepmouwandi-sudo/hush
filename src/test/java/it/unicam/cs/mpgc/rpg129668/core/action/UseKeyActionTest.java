package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Door;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseKeyActionTest {

    private GameState newState(Room startingRoom) {
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(startingRoom));
    }

    @Test
    void correctKeyOpensTheObstacle() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        Door door = new Door("chiave-ufficio");
        corridoio.addExit("est", ufficio, door);

        GameState state = newState(corridoio);
        state.addItem(new Key("chiave-ufficio", "Chiave", "...", "chiave-ufficio"));

        new UseKeyAction("chiave-ufficio", "est").execute(state);

        assertTrue(door.isOpen());
    }

    @Test
    void missingKeyDoesNotOpenAnything() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        Door door = new Door("chiave-ufficio");
        corridoio.addExit("est", ufficio, door);

        GameState state = newState(corridoio);

        new UseKeyAction("chiave-ufficio", "est").execute(state);

        assertFalse(door.isOpen());
    }
}