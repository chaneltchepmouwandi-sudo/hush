package it.unicam.cs.mpgc.rpg129668.core.model.character;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PatrolBehaviorTest {

    @Test
    void movesToNextRoomInRoute() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room sala = new Room("sala", "Sala comune", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");

        PatrolBehavior patrol = new PatrolBehavior(List.of(corridoio, sala, ufficio));
        Guard guard = new Guard("Guardia Rossi", patrol, corridoio);

        guard.tick();

        assertEquals(sala, guard.getCurrentRoom());
    }

    @Test
    void loopsBackToStartAfterLastRoom() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room sala = new Room("sala", "Sala comune", "...");

        PatrolBehavior patrol = new PatrolBehavior(List.of(corridoio, sala));
        Guard guard = new Guard("Guardia Rossi", patrol, corridoio);

        guard.tick(); // -> sala
        guard.tick(); // -> corridoio (ricomincia)

        assertEquals(corridoio, guard.getCurrentRoom());
    }

    @Test
    void emptyRouteIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PatrolBehavior(List.of()));
    }
}