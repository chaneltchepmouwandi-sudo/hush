package it.unicam.cs.mpgc.rpg129668.persistence;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GameStateMapperTest {

    @Test
    void roundTripPreservesGameState() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Room sala = new Room("sala", "Sala comune", "...");
        corridoio.addExit("nord", sala, null);
        Map<String, Room> roomsById = Map.of("corridoio", corridoio, "sala", sala);

        Item peluche = new GenericItem("peluche", "Peluche", "...");
        Map<String, Item> itemsById = Map.of("peluche", peluche);

        GameState original = new GameState(new Player("Protagonista", new StatBlock()), new Position(corridoio));
        original.getPlayer().getStats().setBase(Stat.CARISMA, 7);
        original.addItem(peluche);
        original.getPosition().moveTo(sala);
        original.unlockKnowledge("sa-luce-corridoio-nord");
        original.changeTrust("nadia", 2);
        original.markRequestCompleted("nadia-oggetto");

        GameStateMapper mapper = new GameStateMapper();
        GameState restored = mapper.fromSnapshot(mapper.toSnapshot(original), roomsById, itemsById);

        assertEquals(7, restored.getPlayer().getStats().getEffectiveValue(Stat.CARISMA));
        assertEquals("sala", restored.getPosition().getCurrentRoom().getId());
        assertTrue(restored.hasItem("peluche"));
        assertTrue(restored.hasKnowledge("sa-luce-corridoio-nord"));
        assertEquals(2, restored.getTrust("nadia"));
        assertTrue(restored.hasCompletedRequest("nadia-oggetto"));
    }

    @Test
    void roomItemsAreAlsoPreserved() {
        Room corridoio = new Room("corridoio", "Corridoio", "...");
        Map<String, Room> roomsById = Map.of("corridoio", corridoio);
        Item key = new GenericItem("chiave-ufficio", "Chiave", "...");
        Map<String, Item> itemsById = Map.of("chiave-ufficio", key);

        GameState original = new GameState(new Player("Protagonista", new StatBlock()), new Position(corridoio));
        original.placeItem(corridoio, key);

        GameStateMapper mapper = new GameStateMapper();
        GameState restored = mapper.fromSnapshot(mapper.toSnapshot(original), roomsById, itemsById);

        assertEquals(1, restored.getItemsInRoom(corridoio).size());
    }
}