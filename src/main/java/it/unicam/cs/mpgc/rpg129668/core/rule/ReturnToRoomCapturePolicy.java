package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

/**
 * Riporta la protagonista in una stanza designata (es. l'ingresso del
 * livello) quando viene scoperta. Oggetti e conoscenze già ottenuti
 * non vengono persi, perché GameState li tiene separati dalla posizione.
 */
public class ReturnToRoomCapturePolicy implements CapturePolicy {

    private final Room returnRoom;

    public ReturnToRoomCapturePolicy(Room returnRoom) {
        this.returnRoom = returnRoom;
    }

    @Override
    public String apply(GameState state) {
        state.getPosition().moveTo(returnRoom);
        return "Sei stata scoperta e riportata indietro.";
    }
}