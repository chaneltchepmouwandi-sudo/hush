package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Decide se una guardia individua la protagonista in un dato momento.
 */
@FunctionalInterface
public interface DetectionRule {
    boolean detects(Guard guard, GameState state);
}