package it.unicam.cs.mpgc.rpg129668.core.model.world;

/**
 * Qualcosa che blocca il passaggio tra due stanze, finché non viene
 * risolto (es. una porta chiusa a chiave, in futuro un enigma).
 * L'ostacolo si risolve con un codice testuale e non con un riferimento
 * a un oggetto (es. Key): così core.model.world non dipende da
 * core.model.item, e anche un enigma può essere risolto con una
 * risposta invece che con un oggetto.
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