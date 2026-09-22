package it.unicam.cs.mpgc.rpg129668.core.model.effect;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatModifierTest {

    @Test
    void applyIncreasesTargetStat() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 5);

        StatModifier modifier = new StatModifier(Stat.CARISMA, 3, 4);
        modifier.apply(stats);

        assertEquals(8, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void removeRestoresOriginalValue() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 5);

        StatModifier modifier = new StatModifier(Stat.CARISMA, 3, 4);
        modifier.apply(stats);
        modifier.remove(stats);

        assertEquals(5, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void tickDecreasesRemainingTurns() {
        StatModifier modifier = new StatModifier(Stat.CARISMA, 3, 4);

        modifier.tick();

        assertEquals(3, modifier.getRemainingTurns());
    }

    @Test
    void remainingTurnsNeverGoesBelowZero() {
        StatModifier modifier = new StatModifier(Stat.CARISMA, 3, 1);

        modifier.tick();
        modifier.tick();

        assertEquals(0, modifier.getRemainingTurns());
    }
}