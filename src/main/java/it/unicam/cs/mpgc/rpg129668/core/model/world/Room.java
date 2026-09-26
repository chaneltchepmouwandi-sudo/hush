package it.unicam.cs.mpgc.rpg129668.core.model.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Una singola stanza dell'ospedale. Conosce le uscite verso altre
 * stanze, ciascuna eventualmente bloccata da un Obstacle.
 */
public class Room {

    private final String id;
    private final String name;
    private final String description;
    private final Map<String, Exit> exits = new HashMap<>();

    public Room(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Room destination, Obstacle obstacle) {
        exits.put(direction, new Exit(destination, obstacle));
    }

    public Optional<Exit> getExit(String direction) {
        return Optional.ofNullable(exits.get(direction));
    }

    /**
     * Un collegamento verso un'altra stanza. obstacle è null se il
     * passaggio è sempre libero, senza bisogno di essere sbloccato.
     */
    public record Exit(Room destination, Obstacle obstacle) {

        public boolean isPassable() {
            return obstacle == null || obstacle.isOpen();
        }
    }
}