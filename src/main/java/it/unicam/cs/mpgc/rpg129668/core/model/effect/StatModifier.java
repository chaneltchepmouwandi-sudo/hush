package it.unicam.cs.mpgc.rpg129668.core.model.effect;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;

/**
 * Modifica una singola statistica di un certo importo per una durata
 * limitata (es. una pillola che aumenta il carisma per 5 turni).
 */
public class StatModifier implements Effect {

    private final Stat targetStat;
    private final int amount;
    private int remainingTurns;

    public StatModifier(Stat targetStat, int amount, int durationInTurns) {
        this.targetStat = targetStat;
        this.amount = amount;
        this.remainingTurns = durationInTurns;
    }

    @Override
    public void apply(StatBlock stats) {
        stats.addModifier(targetStat, amount);
    }

    @Override
    public void remove(StatBlock stats) {
        stats.removeModifier(targetStat, amount);
    }

    @Override
    public int getRemainingTurns() {
        return remainingTurns;
    }

    @Override
    public void tick() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }
}