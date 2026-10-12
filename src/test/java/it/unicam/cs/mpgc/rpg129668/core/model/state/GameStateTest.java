package it.unicam.cs.mpgc.rpg129668.core.model.state;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameStateTest {

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        Player player = new Player("Protagonista", new StatBlock());
        return new GameState(player, new Position(stanza));
    }

    private Key newKey() {
        return new Key("chiave-ufficio", "Chiave dell'ufficio", "...", "chiave-ufficio");
    }

    @Test
    void addedItemIsInInventory() {
        GameState state = newState();
        state.addItem(newKey());

        assertTrue(state.hasItem("chiave-ufficio"));
    }

    @Test
    void removedItemIsNoLongerInInventory() {
        GameState state = newState();
        state.addItem(newKey());

        state.removeItem("chiave-ufficio");

        assertFalse(state.hasItem("chiave-ufficio"));
    }

    @Test
    void unlockedKnowledgeIsRemembered() {
        GameState state = newState();
        state.unlockKnowledge("sa-luce-corridoio-nord");

        assertTrue(state.hasKnowledge("sa-luce-corridoio-nord"));
    }

    @Test
    void nullKnowledgeIsIgnored() {
        GameState state = newState();
        state.unlockKnowledge(null);

        assertTrue(state.getUnlockedKnowledge().isEmpty());
    }

    @Test
    void trustStartsAtZeroAndAccumulates() {
        GameState state = newState();
        assertEquals(0, state.getTrust("nadia"));

        state.changeTrust("nadia", 2);
        state.changeTrust("nadia", 1);

        assertEquals(3, state.getTrust("nadia"));
    }

    @Test
    void addEffectAppliesItImmediately() {
        GameState state = newState();
        state.getPlayer().getStats().setBase(Stat.CARISMA, 5);

        state.addEffect(new StatModifier(Stat.CARISMA, 3, 2));

        assertEquals(8, state.getPlayer().getStats().getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void expiredEffectIsRemovedAndStatsRestored() {
        GameState state = newState();
        state.getPlayer().getStats().setBase(Stat.CARISMA, 5);
        state.addEffect(new StatModifier(Stat.CARISMA, 3, 2));

        state.tick();
        assertEquals(8, state.getPlayer().getStats().getEffectiveValue(Stat.CARISMA)); // ancora attivo

        state.tick();
        assertEquals(5, state.getPlayer().getStats().getEffectiveValue(Stat.CARISMA));
        assertTrue(state.getActiveEffects().isEmpty());
    }

    @Test
    void placedItemAppearsInRoom() {
        GameState state = newState();
        Room room = state.getPosition().getCurrentRoom();
        state.placeItem(room, newKey());

        assertEquals(1, state.getItemsInRoom(room).size());
    }

    @Test
    void removingItemFromRoomTakesItOutOfTheRoom() {
        GameState state = newState();
        Room room = state.getPosition().getCurrentRoom();
        state.placeItem(room, newKey());

        Optional<Item> removed = state.removeItemFromRoom(room, "chiave-ufficio");

        assertTrue(removed.isPresent());
        assertTrue(state.getItemsInRoom(room).isEmpty());
    }

    @Test
    void removingMissingItemFromRoomReturnsEmpty() {
        GameState state = newState();
        Room room = state.getPosition().getCurrentRoom();

        assertTrue(state.removeItemFromRoom(room, "non-esiste").isEmpty());
    }
}