package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Decide cosa succede alla protagonista quando viene scoperta da una guardia.
 */
@FunctionalInterface
public interface CapturePolicy {
    String apply(GameState state);
}