package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Pill;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import java.util.Optional;

/**
 * Usa una pillola dall'inventario, applicandone l'effetto e togliendola
 * dall'inventario dopo l'uso.
 */
public class UsePillAction implements GameAction {

    private final String pillId;

    public UsePillAction(String pillId) {
        this.pillId = pillId;
    }

    @Override
    public String execute(GameState state) {
        Optional<Item> item = state.getItem(pillId);
        if (item.isEmpty() || !(item.get() instanceof Pill pill)) {
            return "Non hai quella pillola.";
        }

        state.addEffect(pill.getEffect());
        state.removeItem(pillId);
        return "Hai usato " + pill.getName() + ".";
    }
}