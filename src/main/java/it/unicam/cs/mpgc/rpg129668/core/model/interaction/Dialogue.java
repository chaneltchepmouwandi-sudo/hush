package it.unicam.cs.mpgc.rpg129668.core.model.interaction;

/**
 * Le battute che un NPC pronuncia a proposito di una sua Request, nei
 * tre momenti possibili: finché non è soddisfatta, nel momento in cui
 * viene soddisfatta, e quando è già stata completata.
 */
public record Dialogue(String whenPending, String whenFulfilled, String whenCompleted) {

    /**
     * Battute usate quando una Request non ne specifica di proprie.
     */
    public static final Dialogue DEFAULT = new Dialogue(
            "Torna quando avrai quello che ti ho chiesto.",
            "Grazie per avermelo portato. Ora posso dirti qualcosa.",
            "Non ho altro da dirti.");
}