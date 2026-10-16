package it.unicam.cs.mpgc.rpg129668.persistence;

import java.util.List;
import java.util.Map;

/**
 * Istantanea serializzabile dello stato dinamico di una partita.
 * Contiene solo id e valori primitivi: la ricostruzione degli oggetti
 * veri (Room, Item, ...) avviene tramite i contenuti già caricati da
 * content, non tramite questa classe.
 *
 * TODO: gli effetti attivi (es. una pillola ancora in corso) non
 *       vengono salvati per ora; dopo un caricamento sono considerati
 *       scaduti
 */
public class GameSnapshot {
    public String playerName;
    public Map<String, Integer> baseStats;
    public String currentRoomId;
    public List<String> inventoryItemIds;
    public Map<String, List<String>> roomItemIds;
    public List<String> unlockedKnowledge;
    public Map<String, Integer> trust;
    public List<String> completedRequestIds;
}