package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import java.util.List;

/**
 * Un favore richiesto da un NPC: se la condizione è soddisfatta, la
 * richiesta può essere completata una sola volta, applicando tutte
 * le ricompense allo stato di gioco.
 */
public class Request {

    private final String id;
    private final Condition condition;
    private final List<Reward> rewards;
    private boolean completed;

    public Request(String id, Condition condition, List<Reward> rewards) {
        this.id = id;
        this.condition = condition;
        this.rewards = rewards;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean canBeFulfilled(GameState state) {
        return !completed && condition.isSatisfiedBy(state);
    }

    /**
     * Applica tutte le ricompense e segna la richiesta come completata.
     * Non fa nulla se non può essere soddisfatta.
     *
     * @return true se le ricompense sono state applicate
     */
    public boolean tryFulfill(GameState state) {
        if (!canBeFulfilled(state)) {
            return false;
        }
        rewards.forEach(reward -> reward.grantTo(state));
        completed = true;
        return true;
    }
}