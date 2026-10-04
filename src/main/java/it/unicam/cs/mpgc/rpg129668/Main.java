package it.unicam.cs.mpgc.rpg129668;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.core.rule.ReturnToRoomCapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.SameRoomDetectionRule;
import it.unicam.cs.mpgc.rpg129668.ui.swing.GameWindow;

import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Room sala = new Room("sala", "Sala comune", "Pazienti seduti in silenzio.");
        corridoio.addExit("nord", sala, null);
        sala.addExit("sud", corridoio, null);

        GameState state = new GameState(new Player("Protagonista", new StatBlock()), new Position(corridoio));
        GameController controller = new GameController(state, List.of(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(corridoio));

        SwingUtilities.invokeLater(() -> new GameWindow(controller).setVisible(true));
    }
}