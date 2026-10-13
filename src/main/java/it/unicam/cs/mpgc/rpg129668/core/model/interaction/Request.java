package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import java.util.List;

/**
 * Un favore richiesto da un NPC: se la condizione è soddisfatta, la
 * richiesta può essere completata una sola volta, applicando tutte
 * le ricompense allo stato di gioco. Request non ha stato proprio:
 * se è stata completata o no si chiede a GameState, così tutto lo
 * stato dinamico della partita resta in un unico posto.
 */
public class Request {

    private final String id;
    private final Condition condition;
    private final List<Reward> rewards;

    public Request(String id, Condition condition, List<Reward> rewards) {
        this.id = id;
        this.condition = condition;
        this.rewards = rewards;
    }

    public String getId() {
        return id;
    }

    public boolean isCompleted(GameState state) {
        return state.hasCompletedRequest(id);
    }

    public boolean canBeFulfilled(GameState state) {
        return !isCompleted(state) && condition.isSatisfiedBy(state);
    }

    /**
     * Applica tutte le ricompense e segna la richiesta come completata
     * in GameState. Non fa nulla se non può essere soddisfatta.
     *
     * @return true se le ricompense sono state applicate
     */
    public boolean tryFulfill(GameState state) {
        if (!canBeFulfilled(state)) {
            return false;
        }
        rewards.forEach(reward -> reward.grantTo(state));
        state.markRequestCompleted(id);
        return true;
    }
}