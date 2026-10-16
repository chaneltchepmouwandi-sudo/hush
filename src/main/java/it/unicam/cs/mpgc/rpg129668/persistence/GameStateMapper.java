package it.unicam.cs.mpgc.rpg129668.persistence;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Converte tra GameState (oggetti veri del dominio) e GameSnapshot
 * (solo id e valori primitivi, serializzabile). La ricostruzione usa
 * le stanze e gli oggetti già caricati da content, individuati per id.
 */
public class GameStateMapper {

    public GameSnapshot toSnapshot(GameState state) {
        GameSnapshot snapshot = new GameSnapshot();
        snapshot.playerName = state.getPlayer().getName();

        snapshot.baseStats = new HashMap<>();
        for (Stat stat : Stat.values()) {
            snapshot.baseStats.put(stat.name(), state.getPlayer().getStats().getBase(stat));
        }

        snapshot.currentRoomId = state.getPosition().getCurrentRoom().getId();
        snapshot.inventoryItemIds = state.getItems().stream().map(Item::getId).toList();

        snapshot.roomItemIds = new HashMap<>();
        state.getRoomItemsSnapshot().forEach((roomId, items) ->
                snapshot.roomItemIds.put(roomId, items.stream().map(Item::getId).toList()));

        snapshot.unlockedKnowledge = new ArrayList<>(state.getUnlockedKnowledge());
        snapshot.trust = new HashMap<>(state.getAllTrust());
        snapshot.completedRequestIds = new ArrayList<>(state.getCompletedRequestIds());
        return snapshot;
    }

    /**
     * Ricostruisce un GameState a partire da uno snapshot, usando le
     * stanze e gli oggetti già caricati da content per risolvere gli id.
     */
    public GameState fromSnapshot(GameSnapshot snapshot, Map<String, Room> roomsById, Map<String, Item> itemsById) {
        StatBlock stats = new StatBlock();
        snapshot.baseStats.forEach((statName, value) -> stats.setBase(Stat.valueOf(statName), value));
        Player player = new Player(snapshot.playerName, stats);

        Room currentRoom = roomsById.get(snapshot.currentRoomId);
        GameState state = new GameState(player, new Position(currentRoom));

        for (String itemId : snapshot.inventoryItemIds) {
            Item item = itemsById.get(itemId);
            if (item != null) {
                state.addItem(item);
            }
        }

        snapshot.roomItemIds.forEach((roomId, itemIds) -> {
            Room room = roomsById.get(roomId);
            if (room != null) {
                for (String itemId : itemIds) {
                    Item item = itemsById.get(itemId);
                    if (item != null) {
                        state.placeItem(room, item);
                    }
                }
            }
        });

        snapshot.unlockedKnowledge.forEach(state::unlockKnowledge);
        snapshot.trust.forEach(state::changeTrust);
        snapshot.completedRequestIds.forEach(state::markRequestCompleted);

        return state;
    }
}