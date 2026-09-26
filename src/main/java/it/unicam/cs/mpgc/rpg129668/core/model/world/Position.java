package it.unicam.cs.mpgc.rpg129668.core.model.world;

/**
 * La posizione corrente della protagonista nel mondo di gioco. Per ora
 * coincide con la stanza in cui si trova; è isolata in una classe
 * propria per poter evolvere in futuro (es. coordinate interne alla
 * stanza) senza toccare chi la usa.
 */
public class Position {

    private Room currentRoom;

    public Position(Room startingRoom) {
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void moveTo(Room room) {
        this.currentRoom = room;
    }
}