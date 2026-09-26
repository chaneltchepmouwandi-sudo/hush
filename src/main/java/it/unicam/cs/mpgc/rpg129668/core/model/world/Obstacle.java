package it.unicam.cs.mpgc.rpg129668.core.model.world;

/**
 * Qualcosa che blocca il passaggio tra due stanze, finché non viene
 * risolto (es. una porta chiusa a chiave, in futuro un enigma).
 *
 * TODO: valutare se sostituire il codice testuale con un tipo dedicato
 *       una volta introdotti gli oggetti in core.model.item (es. Key)
 */
public interface Obstacle {

    /**
     * @return true se l'ostacolo non blocca più il passaggio
     */
    boolean isOpen();

    /**
     * Tenta di risolvere l'ostacolo con il codice fornito
     * (es. l'identificativo di una chiave, la risposta a un enigma).
     */
    boolean tryResolve(String code);
}