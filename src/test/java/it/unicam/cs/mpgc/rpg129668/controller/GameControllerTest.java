package it.unicam.cs.mpgc.rpg129668.controller;

import it.unicam.cs.mpgc.rpg129668.core.event.EventType;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.PatrolBehavior;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.core.rule.ReturnToRoomCapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.SameRoomDetectionRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest {

    @Test
    void performingActionEmitsTurnResultEvent() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        GameController controller = new GameController(state, List.of(), List.of(), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        List<GameEvent> events = new ArrayList<>();
        controller.addListener(events::add);

        controller.performAction(s -> "Messaggio di prova");

        assertEquals(EventType.TURN_RESULT, events.get(0).type());
        assertEquals("Messaggio di prova", events.get(0).message());
    }

    @Test
    void turnEndedIsTheLastEventOfATurn() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        GameController controller = new GameController(state, List.of(), List.of(), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        List<GameEvent> events = new ArrayList<>();
        controller.addListener(events::add);

        controller.performAction(s -> "Ti muovi.");

        assertEquals(EventType.TURN_ENDED, events.get(events.size() - 1).type());
    }

    @Test
    void expiredEffectsAreAlreadyRemovedWhenTurnEnds() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        state.getPlayer().getStats().setBase(Stat.CARISMA, 5);
        state.addEffect(new StatModifier(Stat.CARISMA, 3, 1));
        GameController controller = new GameController(state, List.of(), List.of(), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        List<Integer> carismaAtTurnEnd = new ArrayList<>();
        controller.addListener(event -> {
            if (event.type() == EventType.TURN_ENDED) {
                carismaAtTurnEnd.add(state.getPlayer().getStats().getEffectiveValue(Stat.CARISMA));
            }
        });

        controller.performAction(s -> "Passa un turno.");

        assertEquals(List.of(5), carismaAtTurnEnd); // il bonus +3 è già scaduto
    }

    @Test
    void guardEnteringPlayerRoomTriggersCapture() {
        Room stanzaA = new Room("a", "Stanza A", "...");
        Room stanzaB = new Room("b", "Stanza B", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanzaA));
        Guard guard = new Guard("Guardia", new PatrolBehavior(List.of(stanzaB, stanzaA)), stanzaB);

        GameController controller = new GameController(state, List.of(guard), List.of(), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanzaA));

        List<GameEvent> events = new ArrayList<>();
        controller.addListener(events::add);

        controller.performAction(s -> "Ti muovi.");

        boolean captured = events.stream().anyMatch(e -> e.type() == EventType.PLAYER_CAPTURED);
        assertTrue(captured);
    }

    @Test
    void npcsInCurrentRoomAreReturnedByGetter() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        Npc elio = new Npc("elio", "Elio", "...");
        elio.placeIn(stanza);

        GameController controller = new GameController(state, List.of(), List.of(elio), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        assertEquals(1, controller.getNpcsInCurrentRoom().size());
        assertEquals("elio", controller.getNpcsInCurrentRoom().get(0).getId());
    }

    @Test
    void allNpcsAreReturnedRegardlessOfRoom() {
        Room stanzaA = new Room("a", "Stanza A", "...");
        Room stanzaB = new Room("b", "Stanza B", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanzaA));
        Npc elio = new Npc("elio", "Elio", "...");
        elio.placeIn(stanzaB);

        GameController controller = new GameController(state, List.of(), List.of(elio), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanzaA));

        assertEquals(1, controller.getNpcs().size());
        assertTrue(controller.getNpcsInCurrentRoom().isEmpty());
    }

    @Test
    void itemsInCurrentRoomAreReturnedByGetter() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        state.placeItem(stanza, new GenericItem("peluche", "Peluche", "..."));

        GameController controller = new GameController(state, List.of(), List.of(), Map.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(stanza));

        assertEquals(1, controller.getItemsInCurrentRoom().size());
    }
}