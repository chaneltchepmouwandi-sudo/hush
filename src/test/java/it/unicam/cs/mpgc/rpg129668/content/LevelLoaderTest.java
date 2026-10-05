package it.unicam.cs.mpgc.rpg129668.content;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LevelLoaderTest {

    @Test
    void loadsStartingRoomWithCorrectData() {
        Room corridoio = new LevelLoader().load("/content/level1.json");

        assertEquals("corridoio", corridoio.getId());
        assertEquals("Corridoio", corridoio.getName());
    }

    @Test
    void connectsExitsBetweenRooms() {
        Room corridoio = new LevelLoader().load("/content/level1.json");

        Room sala = corridoio.getExit("nord").orElseThrow().destination();
        assertEquals("sala", sala.getId());
    }

    @Test
    void lockedExitHasAClosedObstacle() {
        Room corridoio = new LevelLoader().load("/content/level1.json");

        assertFalse(corridoio.getExit("est").orElseThrow().isPassable());
    }

    @Test
    void throwsWhenResourceIsMissing() {
        LevelLoader loader = new LevelLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.load("/content/does-not-exist.json"));
    }
}