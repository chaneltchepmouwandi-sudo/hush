package it.unicam.cs.mpgc.rpg129668.core.model.effect;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import java.util.EnumMap;
import java.util.Map;

/**
 * Riduce temporaneamente una o più statistiche legate al disorientamento
 * (es. percezione e agilità), rendendo più difficile notare indizi,
 * guardie, o muoversi con sicurezza.
 *
 * TODO: estendere con inversione dei comandi di movimento o sostituzione casuale dei comandi
 */
public class Confusion implements Effect {

    private final Map<Stat, Integer> penalties;
    private int remainingTurns;

    public Confusion(Map<Stat, Integer> penalties, int durationInTurns) {
        this.penalties = new EnumMap<>(penalties);
        this.remainingTurns = durationInTurns;
    }

    @Override
    public void apply(StatBlock stats) {
        penalties.forEach((stat, amount) -> stats.addModifier(stat, -amount));
    }

    @Override
    public void remove(StatBlock stats) {
        penalties.forEach((stat, amount) -> stats.removeModifier(stat, -amount));
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