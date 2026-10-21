package it.unicam.cs.mpgc.rpg129668.core.event;

/**
 * Tipi di evento emessi dal motore di gioco. TURN_RESULT copre il
 * messaggio di ogni azione; PLAYER_CAPTURED segnala la cattura della
 * protagonista; TURN_ENDED è emesso alla fine di ogni turno, dopo che
 * effetti e guardie sono avanzati, e non porta un messaggio da
 * mostrare: serve a chi deve aggiornare la propria vista dello stato.
 * Altri tipi più specifici potranno essere aggiunti quando la UI ne
 * avrà bisogno per reagire in modo diverso caso per caso.
 */
public enum EventType {
    TURN_RESULT,
    PLAYER_CAPTURED,
    TURN_ENDED
}   