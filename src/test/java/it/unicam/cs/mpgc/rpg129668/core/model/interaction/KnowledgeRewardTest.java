package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KnowledgeRewardTest {

    @Test
    void unlocksTheGivenKnowledge() {
        Room stanza = new Room("stanza", "Stanza", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(stanza));

        new KnowledgeReward("sa-infermiera-ala-nord").grantTo(state);

        assertTrue(state.hasKnowledge("sa-infermiera-ala-nord"));
    }
}