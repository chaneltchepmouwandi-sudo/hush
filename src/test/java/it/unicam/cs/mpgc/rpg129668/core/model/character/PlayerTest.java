package it.unicam.cs.mpgc.rpg129668.core.model.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void exposesNameAndStats() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 5);

        Player player = new Player("Protagonista", stats);

        assertEquals("Protagonista", player.getName());
        assertEquals(5, player.getStats().getEffectiveValue(Stat.CARISMA));
    }
}