package it.unicam.cs.mpgc.rpg129668.core.model.world;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomTest {

    @Test
    void exitWithoutObstacleIsAlwaysPassable() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Room sala = new Room("sala", "Sala comune", "Pazienti seduti in silenzio.");

        corridoio.addExit("nord", sala, null);

        assertTrue(corridoio.getExit("nord").get().isPassable());
    }

    @Test
    void exitWithLockedObstacleIsNotPassable() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Room ufficio = new Room("ufficio", "Ufficio infermiera", "Scrivania in ordine.");
        Door door = new Door("chiave-ufficio");

        corridoio.addExit("est", ufficio, door);

        assertFalse(corridoio.getExit("est").get().isPassable());
    }

    @Test
    void exitBecomesPassableOnceObstacleIsResolved() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Room ufficio = new Room("ufficio", "Ufficio infermiera", "Scrivania in ordine.");
        Door door = new Door("chiave-ufficio");

        corridoio.addExit("est", ufficio, door);
        door.tryResolve("chiave-ufficio");

        assertTrue(corridoio.getExit("est").get().isPassable());
    }

    @Test
    void missingExitReturnsEmpty() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");

        assertTrue(corridoio.getExit("sud").isEmpty());
    }
}