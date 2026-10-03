package it.unicam.cs.mpgc.rpg129668.core.event;

/**
 * Tipi di evento emessi dal motore di gioco. TURN_RESULT copre il
 * messaggio di ogni azione; altri tipi più specifici (es. ITEM_ADDED)
 * potranno essere aggiunti quando la UI ne avrà bisogno per reagire
 * in modo diverso caso per caso.
 */
public enum EventType {
    TURN_RESULT,
    PLAYER_CAPTURED
}