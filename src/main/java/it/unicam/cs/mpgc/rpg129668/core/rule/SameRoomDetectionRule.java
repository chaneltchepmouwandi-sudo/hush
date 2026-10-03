package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * La guardia individua la protagonista se si trovano nella stessa stanza.
 *
 * TODO: sostituire con una regola basata su distanza/raggio visivo,
 *       quando il modello avrà coordinate spaziali oltre alla stanza
 */
public class SameRoomDetectionRule implements DetectionRule {

    @Override
    public boolean detects(Guard guard, GameState state) {
        return guard.getCurrentRoom().equals(state.getPosition().getCurrentRoom());
    }
}