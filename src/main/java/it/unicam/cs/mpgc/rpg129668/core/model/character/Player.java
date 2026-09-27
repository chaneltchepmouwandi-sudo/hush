package it.unicam.cs.mpgc.rpg129668.core.model.character;

/**
 * La protagonista del gioco. Per ora rappresenta solo identità e
 * statistiche; inventario, posizione ed effetti attivi sono gestiti
 * altrove (in GameState), non qui.
 *
 * TODO: valutare se aggiungere qui gli effetti attivi, o se lasciarli
 *       centralizzati in GameState per semplicità del ciclo di gioco
 */
public class Player {

    private final String name;
    private final StatBlock stats;

    public Player(String name, StatBlock stats) {
        this.name = name;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public StatBlock getStats() {
        return stats;
    }
}