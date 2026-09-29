package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * L'effetto concesso al giocatore quando una Request viene soddisfatta.
 */
@FunctionalInterface
public interface Reward {
    void grantTo(GameState state);
}