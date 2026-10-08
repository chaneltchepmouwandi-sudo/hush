package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Parla con un NPC, tentando di soddisfare una sua Request se presente.
 * Se l'NPC non ha nessuna Request, mostra semplicemente la sua descrizione.
 */
public class TalkAction implements GameAction {

    private final Npc npc;
    private final Request request;

    public TalkAction(Npc npc) {
        this(npc, null);
    }

    public TalkAction(Npc npc, Request request) {
        this.npc = npc;
        this.request = request;
    }

    @Override
    public String execute(GameState state) {
        if (request == null) {
            return npc.getName() + ": " + npc.getDescription();
        }
        if (request.isCompleted()) {
            return npc.getName() + ": Non ho altro da dirti.";
        }
        if (request.tryFulfill(state)) {
            return npc.getName() + ": Grazie per avermelo portato. Ora posso dirti qualcosa.";
        }
        return npc.getName() + ": Torna quando avrai quello che ti ho chiesto.";
    }
}