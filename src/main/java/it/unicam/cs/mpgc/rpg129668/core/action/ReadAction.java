package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Readable;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import java.util.Optional;

/**
 * Legge un oggetto Readable dall'inventario, sbloccando la conoscenza
 * eventualmente associata.
 */
public class ReadAction implements GameAction {

    private final String itemId;

    public ReadAction(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String execute(GameState state) {
        Optional<Item> item = state.getItem(itemId);
        if (item.isEmpty() || !(item.get() instanceof Readable readable)) {
            return "Non hai nulla da leggere con questo nome.";
        }

        state.unlockKnowledge(readable.getUnlockedKnowledgeId());
        return readable.getContent();
    }
}