package it.unicam.cs.mpgc.rpg129668.content.level;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Door;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Obstacle;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Carica un livello (stanze e collegamenti) da un file JSON nelle
 * risorse del progetto, costruendo gli oggetti Room del dominio.
 */
public class LevelLoader {

    private final Gson gson = new Gson();

    /**
     * @param resourcePath percorso del file JSON nelle risorse (es. "/content/level1.json")
     * @return la stanza di partenza del livello, con tutte le uscite già collegate
     */
    public Room load(String resourcePath) {
        LevelData data = readLevelData(resourcePath);
        Map<String, Room> roomsById = createRooms(data);
        linkExits(data, roomsById);

        Room startingRoom = roomsById.get(data.startingRoom);
        if (startingRoom == null) {
            throw new IllegalStateException("Stanza di partenza non trovata: " + data.startingRoom);
        }
        return startingRoom;
    }

    private LevelData readLevelData(String resourcePath) {
        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Risorsa non trovata: " + resourcePath);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            return gson.fromJson(reader, LevelData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Errore nella lettura di " + resourcePath, e);
        }
    }

    private Map<String, Room> createRooms(LevelData data) {
        Map<String, Room> roomsById = new HashMap<>();
        for (RoomData roomData : data.rooms) {
            roomsById.put(roomData.id, new Room(roomData.id, roomData.name, roomData.description));
        }
        return roomsById;
    }

    private void linkExits(LevelData data, Map<String, Room> roomsById) {
        for (RoomData roomData : data.rooms) {
            Room room = roomsById.get(roomData.id);
            for (ExitData exitData : roomData.exits) {
                Room destination = roomsById.get(exitData.destination);
                Obstacle obstacle = createObstacle(exitData.obstacle);
                room.addExit(exitData.direction, destination, obstacle);
            }
        }
    }

    private Obstacle createObstacle(ObstacleData obstacleData) {
        if (obstacleData == null) {
            return null;
        }
        return switch (obstacleData.type) {
            case "door" -> new Door(obstacleData.code);
            default -> throw new IllegalArgumentException("Tipo di ostacolo sconosciuto: " + obstacleData.type);
        };
    }
}