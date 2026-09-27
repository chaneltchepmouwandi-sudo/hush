package it.unicam.cs.mpgc.rpg129668.core.model.item;

/**
 * Una chiave che sblocca un Obstacle con l'identificativo corrispondente
 * (es. una Door che richiede lo stesso codice).
 */
public class Key implements Item {

    private final String id;
    private final String name;
    private final String description;
    private final String unlockCode;

    public Key(String id, String name, String description, String unlockCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unlockCode = unlockCode;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getUnlockCode() {
        return unlockCode;
    }
}