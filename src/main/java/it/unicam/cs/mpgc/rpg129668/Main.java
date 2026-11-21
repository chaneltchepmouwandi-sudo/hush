package it.unicam.cs.mpgc.rpg129668;

import it.unicam.cs.mpgc.rpg129668.content.item.ItemContent;
import it.unicam.cs.mpgc.rpg129668.content.item.ItemLoader;
import it.unicam.cs.mpgc.rpg129668.content.level.Level;
import it.unicam.cs.mpgc.rpg129668.content.level.LevelLoader;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcContent;
import it.unicam.cs.mpgc.rpg129668.content.npc.NpcLoader;
import it.unicam.cs.mpgc.rpg129668.content.player.PlayerLoader;
import it.unicam.cs.mpgc.rpg129668.controller.GameController;
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

    /**
     * I contenuti di una partita: livello, NPC e oggetti. Vengono
     * caricati da zero a ogni partita, perché alcuni hanno stato (le
     * pillole contano i turni rimanenti del loro effetto) e non devono
     * essere condivisi tra partite diverse.
     */
    private record GameContent(Level level, NpcContent npcs, ItemContent items) {}

    public static void main(String[] args) {
        SaveRepository saveRepository = new JsonSaveRepository(Path.of("hush-save.json"));
        GameStateMapper mapper = new GameStateMapper();

        Supplier<GameController> newGame = () -> {
            GameContent content = loadContent();
            return buildController(content, newGameState(content));
        };
        Supplier<GameController> loadGame = () -> {
            GameContent content = loadContent();
            GameSnapshot snapshot = saveRepository.load();
            GameState state = mapper.fromSnapshot(snapshot, content.level().roomsById(), content.items().itemsById());
            return buildController(content, state);
        };
        Consumer<GameController> onSave = controller -> saveRepository.save(mapper.toSnapshot(controller.getState()));

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(newGame, loadGame, saveRepository.hasSave(), onSave);
            window.setVisible(true);
        });
    }

    private static GameContent loadContent() {
        Level level = new LevelLoader().load("/content/level1.json");
        NpcContent npcs = new NpcLoader().load("/content/npcs.json", level.roomsById());
        ItemContent items = new ItemLoader().load("/content/items.json");
        return new GameContent(level, npcs, items);
    }

    /**
     * Stato di una nuova partita: la protagonista con le statistiche
     * iniziali definite in player.json, nella stanza di partenza, e gli
     * oggetti posizionati nelle stanze.
     */
    private static GameState newGameState(GameContent content) {
        GameState state = new GameState(new PlayerLoader().load("/content/player.json"),
                new Position(content.level().startingRoom()));
        content.items().itemsByRoomId().forEach((roomId, items) -> {
            Room room = content.level().roomsById().get(roomId);
            if (room != null) {
                items.forEach(item -> state.placeItem(room, item));
            }
        });
        return state;
    }

    private static GameController buildController(GameContent content, GameState state) {
        return new GameController(state, List.of(), content.npcs().npcs(), content.npcs().requestsByNpcId(),
                new SameRoomDetectionRule(), new ReturnToRoomCapturePolicy(content.level().startingRoom()));
    }
}