package it.unicam.cs.mpgc.rpg129668.content.npc;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Dialogue;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NpcLoaderTest {

    private Map<String, Room> testRooms() {
        return Map.of(
                "sala", new Room("sala", "Sala comune", "..."),
                "ufficio", new Room("ufficio", "Ufficio", "...")
        );
    }

    private GameState newState() {
        Room stanza = new Room("stanza", "Stanza", "...");
        return new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
    }

    @Test
    void loadsAllNpcs() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());

        assertEquals(3, content.npcs().size());
        assertTrue(content.npcs().stream().anyMatch(npc -> npc.getId().equals("nadia")));
    }

    @Test
    void placesNpcsInTheCorrespondingRoom() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());

        Npc nadia = content.npcs().stream().filter(n -> n.getId().equals("nadia")).findFirst().orElseThrow();
        assertEquals("sala", nadia.getCurrentRoom().getId());
    }

    @Test
    void loadsRequestOnlyForNpcsThatHaveOne() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());

        assertTrue(content.requestsByNpcId().containsKey("nadia"));
        assertTrue(content.requestsByNpcId().containsKey("calla"));
        assertFalse(content.requestsByNpcId().containsKey("elio"));
    }

    @Test
    void loadedRequestWorksEndToEnd() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());
        Request request = content.requestsByNpcId().get("nadia");

        GameState state = newState();

        assertFalse(request.tryFulfill(state));

        state.addItem(peluche());
        assertTrue(request.tryFulfill(state));
        assertEquals(2, state.getTrust("nadia"));
        assertTrue(state.hasKnowledge("sa-infermiera-ala-nord"));
    }

    @Test
    void callaRequestNeedsEnoughCharisma() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());
        Request request = content.requestsByNpcId().get("calla");

        GameState state = newState();
        state.getPlayer().getStats().setBase(Stat.CARISMA, 2);
        assertFalse(request.canBeFulfilled(state));

        state.getPlayer().getStats().setBase(Stat.CARISMA, 3);
        assertTrue(request.canBeFulfilled(state));
    }

    @Test
    void loadedRequestUsesItsOwnDialogueLines() {
        NpcContent content = new NpcLoader().load("/content/npcs.json", testRooms());

        Dialogue lines = content.requestsByNpcId().get("calla").getDialogue();

        assertNotEquals(Dialogue.DEFAULT, lines);
    }

    private Item peluche() {
        return new Item() {
            @Override public String getId() { return "peluche"; }
            @Override public String getName() { return "Peluche"; }
            @Override public String getDescription() { return "..."; }
        };
    }
}