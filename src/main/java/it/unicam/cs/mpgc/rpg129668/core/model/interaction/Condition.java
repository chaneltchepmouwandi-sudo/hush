package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Una condizione verificabile sullo stato di gioco, usata per decidere
 * se una Request può essere soddisfatta.
 */
@FunctionalInterface
public interface Condition {
    boolean isSatisfiedBy(GameState state);
}