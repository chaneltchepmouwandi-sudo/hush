package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Sblocca una conoscenza nello stato di gioco (es. un indizio rivelato
 * a voce da un NPC, senza passare da un documento).
 */
public class KnowledgeReward implements Reward {

    private final String knowledgeId;

    public KnowledgeReward(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    @Override
    public void grantTo(GameState state) {
        state.unlockKnowledge(knowledgeId);
    }
}   