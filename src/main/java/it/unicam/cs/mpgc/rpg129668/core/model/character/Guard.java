package it.unicam.cs.mpgc.rpg129668.core.model.character;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

/**
 * Una guardia o un infermiere che pattuglia l'edificio. Il movimento
 * concreto è delegato a un Behavior, così la strategia di
 * pattugliamento può cambiare senza modificare questa classe.
 */
public class Guard {

    private final String name;
    private final Behavior behavior;
    private Room currentRoom;

    public Guard(String name, Behavior behavior, Room startingRoom) {
        this.name = name;
        this.behavior = behavior;
        this.currentRoom = startingRoom;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void moveTo(Room room) {
        this.currentRoom = room;
    }

    public void tick() {
        behavior.update(this);
    }
}