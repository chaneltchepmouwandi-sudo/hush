package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Aumenta (o diminuisce) la fiducia di un NPC verso il giocatore.
 */
public class TrustReward implements Reward {

    private final String npcId;
    private final int amount;

    public TrustReward(String npcId, int amount) {
        this.npcId = npcId;
        this.amount = amount;
    }

    @Override
    public void grantTo(GameState state) {
        state.changeTrust(npcId, amount);
    }
}