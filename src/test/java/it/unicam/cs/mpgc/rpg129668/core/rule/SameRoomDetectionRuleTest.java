package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.character.PatrolBehavior;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SameRoomDetectionRuleTest {

    @Test
    void detectsWhenInSameRoom() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(corridoio));
        Guard guard = new Guard("Guardia", new PatrolBehavior(List.of(corridoio)), corridoio);

        assertTrue(new SameRoomDetectionRule().detects(guard, state));
    }

    @Test
    void doesNotDetectWhenInDifferentRoom() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room sala = new Room("sala", "Sala comune", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(corridoio));
        Guard guard = new Guard("Guardia", new PatrolBehavior(List.of(sala)), sala);

        assertFalse(new SameRoomDetectionRule().detects(guard, state));
    }
}