package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Dialogue;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Parla con un NPC, tentando di soddisfare una sua Request se presente
 * e rispondendo con le battute definite nella Request stessa.
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
        Dialogue dialogue = request.getDialogue();
        if (request.isCompleted(state)) {
            return npc.getName() + ": " + dialogue.whenCompleted();
        }
        if (request.tryFulfill(state)) {
            return npc.getName() + ": " + dialogue.whenFulfilled();
        }
        return npc.getName() + ": " + dialogue.whenPending();
    }
}