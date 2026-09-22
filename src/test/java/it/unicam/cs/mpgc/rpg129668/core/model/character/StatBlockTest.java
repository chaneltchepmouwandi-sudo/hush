package it.unicam.cs.mpgc.rpg129668.core.model.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatBlockTest {

    @Test
    void baseValueIsZeroByDefault() {
        StatBlock stats = new StatBlock();
        assertEquals(0, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void setBaseChangesEffectiveValue() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 5);
        assertEquals(5, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void addModifierIncreasesEffectiveValue() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 5);
        stats.addModifier(Stat.PERCEZIONE, 3);
        assertEquals(8, stats.getEffectiveValue(Stat.PERCEZIONE));
    }

    @Test
    void removeModifierDecreasesEffectiveValue() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 5);
        stats.addModifier(Stat.PERCEZIONE, 3);
        stats.removeModifier(Stat.PERCEZIONE, 3);
        assertEquals(5, stats.getEffectiveValue(Stat.PERCEZIONE));
    }

    @Test
    void statBlocksWithSameValuesAreEqual() {
        StatBlock a = new StatBlock();
        a.setBase(Stat.CARISMA, 3);

        StatBlock b = new StatBlock();
        b.setBase(Stat.CARISMA, 3);

        assertEquals(a, b);
    }
}