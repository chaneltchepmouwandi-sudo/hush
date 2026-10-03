package it.unicam.cs.mpgc.rpg129668.core.rule;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnToRoomCapturePolicyTest {

    @Test
    void movesPlayerBackToDesignatedRoom() {
        Room ingresso = new Room("ingresso", "Ingresso", "...");
        Room ufficio = new Room("ufficio", "Ufficio", "...");
        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(ufficio));

        new ReturnToRoomCapturePolicy(ingresso).apply(state);

        assertEquals(ingresso, state.getPosition().getCurrentRoom());
    }
}