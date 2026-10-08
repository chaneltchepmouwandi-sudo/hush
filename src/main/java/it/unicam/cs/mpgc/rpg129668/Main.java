package it.unicam.cs.mpgc.rpg129668;

import it.unicam.cs.mpgc.rpg129668.content.level.Level;
import it.unicam.cs.mpgc.rpg129668.content.level.LevelLoader;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcContent;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcLoader;
import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.rule.ReturnToRoomCapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.SameRoomDetectionRule;
import it.unicam.cs.mpgc.rpg129668.ui.swing.GameWindow;

import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Level level = new LevelLoader().load("/content/level1.json");
        NpcContent npcContent = new NpcLoader().load("/content/npcs.json", level.roomsById());

        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(level.startingRoom()));
        GameController controller = new GameController(state, List.of(), npcContent.npcs(), npcContent.requestsByNpcId(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(level.startingRoom()));

        SwingUtilities.invokeLater(() -> new GameWindow(controller).setVisible(true));
    }
}