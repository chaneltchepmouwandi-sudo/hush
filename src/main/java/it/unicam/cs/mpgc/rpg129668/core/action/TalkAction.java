package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Parla con un NPC, tentando di soddisfare una sua Request se presente.
 */
public class TalkAction implements GameAction {

    private final Request request;

    public TalkAction(Request request) {
        this.request = request;
    }

    @Override
    public String execute(GameState state) {
        if (request.isCompleted()) {
            return "Non hai altro da dirmi.";
        }
        if (request.tryFulfill(state)) {
            return "Grazie per avermelo portato. Ora posso dirti qualcosa.";
        }
        return "Torna quando avrai quello che ti ho chiesto.";
    }
}