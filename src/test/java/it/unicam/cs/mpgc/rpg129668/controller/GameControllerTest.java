package it.unicam.cs.mpgc.rpg129668.controller;

import it.unicam.cs.mpgc.rpg129668.core.event.EventType;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.character.PatrolBehavior;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.core.rule.ReturnToRoomCapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.SameRoomDetectionRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest {

    @Test
    void performingActionEmitsTurnResultEvent() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        GameController controller = new GameController(state, List.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        List<GameEvent> events = new ArrayList<>();
        controller.addListener(events::add);

        controller.performAction(s -> "Messaggio di prova");

        assertEquals(1, events.size());
        assertEquals(EventType.TURN_RESULT, events.get(0).type());
        assertEquals("Messaggio di prova", events.get(0).message());
    }

    @Test
    void guardEnteringPlayerRoomTriggersCapture() {
        Room stanzaA = new Room("a", "Stanza A", "...");
        Room stanzaB = new Room("b", "Stanza B", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanzaA));
        Guard guard = new Guard("Guardia", new PatrolBehavior(List.of(stanzaB, stanzaA)), stanzaB);

        GameController controller = new GameController(state, List.of(guard),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanzaA));

        List<GameEvent> events = new ArrayList<>();
        controller.addListener(events::add);

        controller.performAction(s -> "Ti muovi.");

        boolean captured = events.stream().anyMatch(e -> e.type() == EventType.PLAYER_CAPTURED);
        assertTrue(captured);
    }
}