package it.unicam.cs.mpgc.rpg129668.content.item;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import java.util.List;
import java.util.Map;

/**
 * Risultato del caricamento degli oggetti: raggruppati per stanza
 * (per posizionarli all'inizio della partita) e indicizzati per id
 * (per ricostruire un salvataggio).
 */
public record ItemContent(Map<String, List<Item>> itemsByRoomId, Map<String, Item> itemsById) {}