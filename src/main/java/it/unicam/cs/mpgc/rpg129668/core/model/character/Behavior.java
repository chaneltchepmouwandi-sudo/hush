package it.unicam.cs.mpgc.rpg129668.core.model.character;

/**
 * Determina come una guardia si muove nell'ambiente a ogni turno.
 * Implementazioni diverse permettono strategie di pattugliamento
 * diverse (es. percorso fisso, casuale) senza modificare Guard.
 */
public interface Behavior {

    /**
     * Fa avanzare il comportamento di un turno, aggiornando la
     * posizione della guardia.
     */
    void update(Guard guard);
}