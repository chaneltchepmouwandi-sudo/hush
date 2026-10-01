package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Door;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveActionTest {

    private GameState newState(Room startingRoom) {
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(startingRoom));
    }

    @Test
    void movesToDestinationWhenExitIsFree() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room sala = new Room("sala", "Sala comune", "...");
        corridoio.addExit("nord", sala, null);
        GameState state = newState(corridoio);

        new MoveAction("nord").execute(state);

        assertEquals(sala, state.getPosition().getCurrentRoom());
    }

    @Test
    void doesNotMoveWhenExitIsMissing() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        GameState state = newState(corridoio);

        new MoveAction("sud").execute(state);

        assertEquals(corridoio, state.getPosition().getCurrentRoom());
    }

    @Test
    void doesNotMoveWhenObstacleIsLocked() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        corridoio.addExit("est", ufficio, new Door("chiave-ufficio"));
        GameState state = newState(corridoio);

        new MoveAction("est").execute(state);

        assertEquals(corridoio, state.getPosition().getCurrentRoom());
    }
}