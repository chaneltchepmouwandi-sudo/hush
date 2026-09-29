package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HasItemConditionTest {

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
    }

    @Test
    void isNotSatisfiedWithoutTheItem() {
        assertFalse(new HasItemCondition("chiave-ufficio").isSatisfiedBy(newState()));
    }

    @Test
    void isSatisfiedOnceItemIsAdded() {
        GameState state = newState();
        state.addItem(new Key("chiave-ufficio", "Chiave", "...", "chiave-ufficio"));

        assertTrue(new HasItemCondition("chiave-ufficio").isSatisfiedBy(state));
    }
}