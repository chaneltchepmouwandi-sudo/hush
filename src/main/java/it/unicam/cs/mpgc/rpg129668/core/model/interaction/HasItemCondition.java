package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Verifica che il giocatore possieda un determinato oggetto.
 */
public class HasItemCondition implements Condition {

    private final String itemId;

    public HasItemCondition(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean isSatisfiedBy(GameState state) {
        return state.hasItem(itemId);
    }
}