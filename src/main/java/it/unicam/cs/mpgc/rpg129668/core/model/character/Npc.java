package it.unicam.cs.mpgc.rpg129668.core.model.character;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

/**
 * Un personaggio non giocante (paziente o membro dello staff).
 * currentRoom è opzionale: un Npc non ancora posizionato ha currentRoom null.
 */
public class Npc {

    private final String id;
    private final String name;
    private final String description;
    private Room currentRoom;

    public Npc(String id, String name, String description) {
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

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void placeIn(Room room) {
        this.currentRoom = room;
    }
}