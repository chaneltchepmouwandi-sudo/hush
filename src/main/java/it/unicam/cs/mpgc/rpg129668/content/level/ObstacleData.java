package it.unicam.cs.mpgc.rpg129668.content.level;

/**
 * Rappresentazione grezza di un Obstacle così come appare nel JSON.
 * "type" seleziona l'implementazione concreta (per ora solo "door").
 */
public class ObstacleData {
    public String type;
    public String code;
}