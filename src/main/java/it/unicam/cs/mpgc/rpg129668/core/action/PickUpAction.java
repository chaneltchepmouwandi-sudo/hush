package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.util.Optional;

/**
 * Raccoglie un oggetto presente nella stanza corrente, spostandolo
 * nell'inventario del giocatore.
 */
public class PickUpAction implements GameAction {

    private final String itemId;

    public PickUpAction(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String execute(GameState state) {
        Room room = state.getPosition().getCurrentRoom();
        Optional<Item> item = state.removeItemFromRoom(room, itemId);

        if (item.isEmpty()) {
            return "Non c'è nulla con questo nome qui.";
        }

        state.addItem(item.get());
        return "Hai raccolto " + item.get().getName() + ".";
    }
}