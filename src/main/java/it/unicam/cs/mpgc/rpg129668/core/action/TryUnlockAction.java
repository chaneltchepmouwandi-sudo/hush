package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

/**
 * Prova ad aprire l'ostacolo in una direzione usando ogni chiave
 * posseduta dal giocatore, finché una non funziona.
 *
 * // TODO: aggiungere test
 */
public class TryUnlockAction implements GameAction {

    private final String direction;

    public TryUnlockAction(String direction) {
        this.direction = direction;
    }

    @Override
    public String execute(GameState state) {
        Room room = state.getPosition().getCurrentRoom();
        var exit = room.getExit(direction);

        if (exit.isEmpty() || exit.get().obstacle() == null) {
            return "Non c'è nulla da aprire in quella direzione.";
        }
        if (exit.get().isPassable()) {
            return "Il passaggio è già aperto.";
        }

        for (Item item : state.getItems()) {
            if (item instanceof Key key && exit.get().obstacle().tryResolve(key.getUnlockCode())) {
                return "Hai aperto il passaggio con " + key.getName() + ".";
            }
        }
        return "Nessuna delle tue chiavi apre questo passaggio.";
    }
}