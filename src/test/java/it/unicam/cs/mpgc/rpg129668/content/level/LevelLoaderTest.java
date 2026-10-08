package it.unicam.cs.mpgc.rpg129668.content.level;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelLoaderTest {

    @Test
    void loadsStartingRoomWithCorrectData() {
        Room corridoio = new LevelLoader().load("/content/level1.json").startingRoom();

        assertEquals("corridoio", corridoio.getId());
        assertEquals("Corridoio", corridoio.getName());
    }

    @Test
    void connectsExitsBetweenRooms() {
        Room corridoio = new LevelLoader().load("/content/level1.json").startingRoom();

        Room sala = corridoio.getExit("nord").orElseThrow().destination();
        assertEquals("sala", sala.getId());
    }

    @Test
    void lockedExitHasAClosedObstacle() {
        Room corridoio = new LevelLoader().load("/content/level1.json").startingRoom();

        assertFalse(corridoio.getExit("est").orElseThrow().isPassable());
    }

    @Test
    void exposesAllRoomsById() {
        Level level = new LevelLoader().load("/content/level1.json");

        assertEquals(3, level.roomsById().size());
        assertTrue(level.roomsById().containsKey("ufficio"));
    }

    @Test
    void throwsWhenResourceIsMissing() {
        LevelLoader loader = new LevelLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.load("/content/does-not-exist.json"));
    }
}