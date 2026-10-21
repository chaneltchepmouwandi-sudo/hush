package it.unicam.cs.mpgc.rpg129668;

import it.unicam.cs.mpgc.rpg129668.content.item.ItemContent;
import it.unicam.cs.mpgc.rpg129668.content.item.ItemLoader;
import it.unicam.cs.mpgc.rpg129668.content.level.Level;
import it.unicam.cs.mpgc.rpg129668.content.level.LevelLoader;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcContent;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcLoader;
import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.core.rule.ReturnToRoomCapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.SameRoomDetectionRule;
import it.unicam.cs.mpgc.rpg129668.persistence.GameSnapshot;
import it.unicam.cs.mpgc.rpg129668.persistence.GameStateMapper;
import it.unicam.cs.mpgc.rpg129668.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg129668.persistence.SaveRepository;
import it.unicam.cs.mpgc.rpg129668.ui.swing.GameWindow;

import javax.swing.SwingUtilities;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Level level = new LevelLoader().load("/content/level1.json");
        NpcContent npcContent = new NpcLoader().load("/content/npcs.json", level.roomsById());
        ItemContent itemContent = new ItemLoader().load("/content/items.json");

        SaveRepository saveRepository = new JsonSaveRepository(Path.of("hush-save.json"));
        GameStateMapper mapper = new GameStateMapper();

        Supplier<GameController> newGame = () -> buildController(level, npcContent, newGameState(level, itemContent));
        Supplier<GameController> loadGame = () -> {
            GameSnapshot snapshot = saveRepository.load();
            GameState state = mapper.fromSnapshot(snapshot, level.roomsById(), itemContent.itemsById());
            return buildController(level, npcContent, state);
        };
        Consumer<GameController> onSave = controller -> saveRepository.save(mapper.toSnapshot(controller.getState()));

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(newGame, loadGame, saveRepository.hasSave(), onSave);
            window.setVisible(true);
        });
    }

    private static GameState newGameState(Level level, ItemContent itemContent) {
        GameState state = new GameState(new Player("Protagonista", initialStats()), new Position(level.startingRoom()));
        itemContent.itemsByRoomId().forEach((roomId, items) -> {
            Room room = level.roomsById().get(roomId);
            if (room != null) {
                items.forEach(item -> state.placeItem(room, item));
            }
        });
        return state;
    }

    /**
     * Statistiche iniziali della protagonista.
     *
     * TODO: spostare i valori iniziali in un file JSON insieme al resto dei contenuti
     */
    private static StatBlock initialStats() {
        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 2);
        stats.setBase(Stat.FORZA, 1);
        stats.setBase(Stat.PERCEZIONE, 3);
        stats.setBase(Stat.AGILITA, 3);
        return stats;
    }

    private static GameController buildController(Level level, NpcContent npcContent, GameState state) {
        return new GameController(state, List.of(), npcContent.npcs(), npcContent.requestsByNpcId(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(level.startingRoom()));
    }
}