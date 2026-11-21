package it.unicam.cs.mpgc.rpg129668.content.player;

import java.util.Map;

/**
 * Rappresentazione grezza della protagonista così come appare nel
 * JSON: nome e valori di base delle statistiche, indicizzati per nome
 * (come in Stat). Le statistiche non elencate partono da zero.
 */
public class PlayerData {
    public String name;
    public Map<String, Integer> stats;
}