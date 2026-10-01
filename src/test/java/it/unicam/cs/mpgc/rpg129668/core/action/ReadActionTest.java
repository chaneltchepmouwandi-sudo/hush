package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Document;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadActionTest {

    @Test
    void readingUnlocksTheAssociatedKnowledge() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));
        state.addItem(new Document("foglio-elio", "Un disegno stropicciato", "...", "sa-luce-corridoio-nord"));

        new ReadAction("foglio-elio").execute(state);

        assertTrue(state.hasKnowledge("sa-luce-corridoio-nord"));
    }
}