package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;

/**
 * Un'azione che il giocatore può compiere. Modifica lo stato di gioco
 * quando serve e restituisce un messaggio descrittivo del risultato.
 */
@FunctionalInterface
public interface GameAction {
    String execute(GameState state);
}