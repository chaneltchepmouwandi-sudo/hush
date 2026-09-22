package it.unicam.cs.mpgc.rpg129668.core.model.effect;

import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;

/**
 * Rappresenta un effetto temporaneo applicabile a uno StatBlock,
 * tipicamente causato dall'uso di un oggetto (es. una pillola).
 * La durata è misurata in turni: chi gestisce il ciclo di gioco
 * chiama tick() a ogni azione del giocatore.
 */
public interface Effect {

    /**
     * Applica l'effetto, tipicamente aggiungendo un modificatore.
     */
    void apply(StatBlock stats);

    /**
     * Annulla l'effetto quando la durata è terminata.
     */
    void remove(StatBlock stats);

    /**
     * @return i turni rimanenti prima che l'effetto termini
     */
    int getRemainingTurns();

    /**
     * Fa avanzare l'effetto di un turno, riducendo la durata rimanente.
     */
    void tick();
}