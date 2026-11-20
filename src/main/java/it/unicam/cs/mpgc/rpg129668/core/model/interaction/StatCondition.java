package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Verifica che una statistica della protagonista abbia almeno un certo
 * valore effettivo, cioè comprensivo dei modificatori temporanei
 * (es. una pillola ancora attiva).
 */
public class StatCondition implements Condition {

    private final Stat stat;
    private final int minimum;

    public StatCondition(Stat stat, int minimum) {
        this.stat = stat;
        this.minimum = minimum;
    }

    @Override
    public boolean isSatisfiedBy(GameState state) {
        return state.getPlayer().getStats().getEffectiveValue(stat) >= minimum;
    }
}