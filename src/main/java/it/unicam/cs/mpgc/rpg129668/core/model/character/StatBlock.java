package it.unicam.cs.mpgc.rpg129668.core.model.character;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contiene i valori base di ogni statistica e i modificatori temporanei
 * applicati da effetti (es. pillole). Il valore effettivo è sempre
 * la somma di base e modificatori correnti.
 */
public class StatBlock {

    private final Map<Stat, Integer> baseValues = new EnumMap<>(Stat.class);
    private final Map<Stat, Integer> modifiers = new EnumMap<>(Stat.class);

    public StatBlock() {
        for (Stat stat : Stat.values()) {
            baseValues.put(stat, 0);
            modifiers.put(stat, 0);
        }
    }

    public void setBase(Stat stat, int value) {
        baseValues.put(stat, value);
    }

    public int getBase(Stat stat) {
        return baseValues.get(stat);
    }

    public void addModifier(Stat stat, int amount) {
        modifiers.merge(stat, amount, Integer::sum);
    }

    public void removeModifier(Stat stat, int amount) {
        modifiers.merge(stat, -amount, Integer::sum);
    }

    public int getEffectiveValue(Stat stat) {
        return baseValues.get(stat) + modifiers.get(stat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatBlock other)) return false;
        return baseValues.equals(other.baseValues) && modifiers.equals(other.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseValues, modifiers);
    }

    @Override
    public String toString() {
        return "StatBlock{base=" + baseValues + ", modifiers=" + modifiers + '}';
    }
}