package it.unicam.cs.mpgc.rpg129668.core.model.effect;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfusionTest {

    @Test
    void applyReducesAllTargetedStats() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 5);
        stats.setBase(Stat.AGILITA, 5);

        Confusion confusion = new Confusion(Map.of(Stat.PERCEZIONE, 3, Stat.AGILITA, 2), 5);
        confusion.apply(stats);

        assertEquals(2, stats.getEffectiveValue(Stat.PERCEZIONE));
        assertEquals(3, stats.getEffectiveValue(Stat.AGILITA));
    }

    @Test
    void removeRestoresAllTargetedStats() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 5);
        stats.setBase(Stat.AGILITA, 5);

        Confusion confusion = new Confusion(Map.of(Stat.PERCEZIONE, 3, Stat.AGILITA, 2), 5);
        confusion.apply(stats);
        confusion.remove(stats);

        assertEquals(5, stats.getEffectiveValue(Stat.PERCEZIONE));
        assertEquals(5, stats.getEffectiveValue(Stat.AGILITA));
    }

    @Test
    void tickDecreasesRemainingTurnsToZero() {
        Confusion confusion = new Confusion(Map.of(Stat.PERCEZIONE, 3), 2);

        confusion.tick();
        confusion.tick();

        assertEquals(0, confusion.getRemainingTurns());
    }

    @Test
    void affectsOnlySpecifiedStats() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 5);
        stats.setBase(Stat.FORZA, 5);

        Confusion confusion = new Confusion(Map.of(Stat.PERCEZIONE, 3), 5);
        confusion.apply(stats);

        assertEquals(2, stats.getEffectiveValue(Stat.PERCEZIONE));
        assertEquals(5, stats.getEffectiveValue(Stat.FORZA)); // non toccata
    }
}