package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatConditionTest {

    private GameState newState(int carisma) {
        Room stanza = new Room("stanza", "Stanza", "...");
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, carisma);
        return new GameState(new Player("Protagonista", stats), new Position(stanza));
    }

    @Test
    void isNotSatisfiedBelowTheMinimum() {
        assertFalse(new StatCondition(Stat.CARISMA, 3).isSatisfiedBy(newState(2)));
    }

    @Test
    void isSatisfiedAtTheMinimum() {
        assertTrue(new StatCondition(Stat.CARISMA, 3).isSatisfiedBy(newState(3)));
    }

    @Test
    void temporaryModifiersCountWhileTheyLast() {
        GameState state = newState(2);
        StatCondition condition = new StatCondition(Stat.CARISMA, 3);

        state.addEffect(new StatModifier(Stat.CARISMA, 1, 1));
        assertTrue(condition.isSatisfiedBy(state));

        state.tick(); // l'effetto scade
        assertFalse(condition.isSatisfiedBy(state));
    }
}