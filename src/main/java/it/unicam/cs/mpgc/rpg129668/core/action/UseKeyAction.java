package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import java.util.Optional;

/**
 * Usa una chiave dell'inventario per tentare di aprire un ostacolo
 * nella direzione indicata.
 */
public class UseKeyAction implements GameAction {

    private final String keyId;
    private final String direction;

    public UseKeyAction(String keyId, String direction) {
        this.keyId = keyId;
        this.direction = direction;
    }

    @Override
    public String execute(GameState state) {
        Optional<Item> item = state.getItem(keyId);
        if (item.isEmpty() || !(item.get() instanceof Key key)) {
            return "Non hai quella chiave.";
        }

        Room currentRoom = state.getPosition().getCurrentRoom();
        Optional<Room.Exit> exit = currentRoom.getExit(direction);
        if (exit.isEmpty() || exit.get().obstacle() == null) {
            return "Non c'è nessun ostacolo da aprire in quella direzione.";
        }

        boolean opened = exit.get().obstacle().tryResolve(key.getUnlockCode());
        return opened ? "Hai aperto il passaggio." : "La chiave non è quella giusta.";
    }
}