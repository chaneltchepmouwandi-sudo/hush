package it.unicam.cs.mpgc.rpg129668.persistence;

/**
 * Salva e carica un'istantanea dello stato di gioco.
 */
public interface SaveRepository {
    void save(GameSnapshot snapshot);
    GameSnapshot load();
    boolean hasSave();
}