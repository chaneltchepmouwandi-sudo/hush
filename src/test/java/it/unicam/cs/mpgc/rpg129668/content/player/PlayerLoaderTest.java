package it.unicam.cs.mpgc.rpg129668.content.player;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerLoaderTest {

    @Test
    void loadsNameAndBaseStats() {
        Player player = new PlayerLoader().load("/content/player.json");

        assertEquals("Protagonista", player.getName());
        assertEquals(2, player.getStats().getBase(Stat.CARISMA));
        assertEquals(1, player.getStats().getBase(Stat.FORZA));
        assertEquals(3, player.getStats().getBase(Stat.PERCEZIONE));
        assertEquals(3, player.getStats().getBase(Stat.AGILITA));
    }

    @Test
    void eachLoadCreatesAnIndependentPlayer() {
        PlayerLoader loader = new PlayerLoader();
        Player first = loader.load("/content/player.json");
        first.getStats().setBase(Stat.CARISMA, 9);

        Player second = loader.load("/content/player.json");

        assertEquals(2, second.getStats().getBase(Stat.CARISMA));
    }

    @Test
    void throwsWhenResourceIsMissing() {
        PlayerLoader loader = new PlayerLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.load("/content/does-not-exist.json"));
    }
}