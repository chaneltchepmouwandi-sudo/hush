package it.unicam.cs.mpgc.rpg129668.core.model.item;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PillTest {

    @Test
    void useAppliesTheAssociatedEffect() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 5);

        Pill pill = new Pill("pillola-carisma", "Pillola blu", "Aumenta il carisma",
                new StatModifier(Stat.CARISMA, 3, 5));
        pill.use(stats);

        assertEquals(8, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void exposesItsOwnIdentity() {
        Pill pill = new Pill("pillola-carisma", "Pillola blu", "Aumenta il carisma",
                new StatModifier(Stat.CARISMA, 3, 5));

        assertEquals("pillola-carisma", pill.getId());
        assertEquals("Pillola blu", pill.getName());
    }
}