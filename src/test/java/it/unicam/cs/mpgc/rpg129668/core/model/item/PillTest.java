package it.unicam.cs.mpgc.rpg129668.core.model.item;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Effect;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PillTest {

    @Test
    void exposesTheEffectItCarries() {
        Effect effect = new StatModifier(Stat.CARISMA, 3, 5);
        Pill pill = new Pill("pillola-carisma", "Pillola blu", "Aumenta il carisma", effect);

        assertSame(effect, pill.getEffect());
    }

    @Test
    void exposesItsOwnIdentity() {
        Pill pill = new Pill("pillola-carisma", "Pillola blu", "Aumenta il carisma",
                new StatModifier(Stat.CARISMA, 3, 5));

        assertEquals("pillola-carisma", pill.getId());
        assertEquals("Pillola blu", pill.getName());
        assertEquals("Aumenta il carisma", pill.getDescription());
    }
}