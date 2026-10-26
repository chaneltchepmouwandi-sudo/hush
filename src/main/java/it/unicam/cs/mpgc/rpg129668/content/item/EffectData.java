package it.unicam.cs.mpgc.rpg129668.content.item;

import java.util.List;

/**
 * Rappresentazione grezza di un Effect così come appare nel JSON.
 * "type" seleziona l'implementazione concreta ("statModifier" o
 * "confusion"); gli altri campi sono usati solo dal tipo che li richiede.
 */
public class EffectData {
    public String type;
    public int duration;
    public String stat;
    public int amount;
    public List<PenaltyData> penalties;
}