package it.unicam.cs.mpgc.rpg129668.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class JsonSaveRepositoryTest {

    @Test
    void hasSaveIsFalseWhenFileDoesNotExist(@TempDir Path tempDir) {
        JsonSaveRepository repository = new JsonSaveRepository(tempDir.resolve("save.json"));
        assertFalse(repository.hasSave());
    }

    @Test
    void savedSnapshotCanBeLoadedBack(@TempDir Path tempDir) {
        JsonSaveRepository repository = new JsonSaveRepository(tempDir.resolve("save.json"));

        GameSnapshot snapshot = new GameSnapshot();
        snapshot.playerName = "Protagonista";
        snapshot.currentRoomId = "corridoio";
        snapshot.baseStats = Map.of("CARISMA", 5);
        snapshot.inventoryItemIds = List.of("peluche");
        snapshot.roomItemIds = Map.of("corridoio", List.of("chiave-ufficio"));
        snapshot.unlockedKnowledge = List.of("sa-luce-corridoio-nord");
        snapshot.trust = Map.of("nadia", 2);
        snapshot.completedRequestIds = List.of("nadia-oggetto");

        repository.save(snapshot);
        assertTrue(repository.hasSave());

        GameSnapshot loaded = repository.load();
        assertEquals("Protagonista", loaded.playerName);
        assertEquals("corridoio", loaded.currentRoomId);
        assertEquals(5, loaded.baseStats.get("CARISMA"));
        assertEquals(List.of("peluche"), loaded.inventoryItemIds);
        assertEquals(2, loaded.trust.get("nadia"));
    }
}