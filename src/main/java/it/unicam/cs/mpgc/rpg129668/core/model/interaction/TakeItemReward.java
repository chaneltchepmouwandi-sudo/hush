package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Toglie un oggetto dall'inventario del giocatore (es. consegnato a un NPC).
 */
public class TakeItemReward implements Reward {

    private final String itemId;

    public TakeItemReward(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public void grantTo(GameState state) {
        state.removeItem(itemId);
    }
}