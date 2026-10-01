package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Pill;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UsePillActionTest {

    @Test
    void usingPillAppliesEffectAndRemovesItFromInventory() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        state.getPlayer().getStats().setBase(Stat.CARISMA, 5);
        state.addItem(new Pill("pillola-carisma", "Pillola blu", "...",
                new StatModifier(Stat.CARISMA, 3, 5)));

        new UsePillAction("pillola-carisma").execute(state);

        assertEquals(8, state.getPlayer().getStats().getEffectiveValue(Stat.CARISMA));
        assertFalse(state.hasItem("pillola-carisma"));
    }
}