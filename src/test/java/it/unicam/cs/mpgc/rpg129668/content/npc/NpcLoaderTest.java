package it.unicam.cs.mpgc.rpg129668.content.npc;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NpcLoaderTest {

    private Map<String, Room> testRooms() {
        return Map.of(
                "sala", new Room("sala", "Sala comune", "..."),
                "ufficio", new Room("ufficio", "Ufficio", "...")
        );
    }

    @Test
    void loadsAllNpcs() {
        List<Npc> npcs = new NpcLoader().load("/content/npcs.json", testRooms());

        assertEquals(3, npcs.size());
        assertTrue(npcs.stream().anyMatch(npc -> npc.getId().equals("nadia")));
    }

    @Test
    void placesNpcsInTheCorrespondingRoom() {
        List<Npc> npcs = new NpcLoader().load("/content/npcs.json", testRooms());

        Npc nadia = npcs.stream().filter(n -> n.getId().equals("nadia")).findFirst().orElseThrow();
        assertEquals("sala", nadia.getCurrentRoom().getId());
    }

    @Test
    void loadsOnlyRequestsThatExist() {
        List<Request> requests = new NpcLoader().loadRequests("/content/npcs.json");

        assertEquals(1, requests.size());
        assertEquals("nadia-oggetto", requests.get(0).getId());
    }

    @Test
    void loadedRequestWorksEndToEnd() {
        Request request = new NpcLoader().loadRequests("/content/npcs.json").get(0);

        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));

        boolean fulfilled = request.tryFulfill(state);
        assertFalse(fulfilled);

        state.addItem(peluche());
        fulfilled = request.tryFulfill(state);

        assertTrue(fulfilled);
        assertEquals(2, state.getTrust("nadia"));
        assertTrue(state.hasKnowledge("sa-infermiera-ala-nord"));
    }

    private Item peluche() {
        return new Item() {
            @Override
            public String getId() { return "peluche"; }
            @Override
            public String getName() { return "Peluche"; }
            @Override
            public String getDescription() { return "..."; }
        };
    }
}