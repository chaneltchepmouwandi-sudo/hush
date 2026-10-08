package it.unicam.cs.mpgc.rpg129668.content.level;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.util.Map;

/**
 * Risultato del caricamento di un livello: la stanza di partenza e
 * tutte le stanze indicizzate per id, utile per collegare altri
 * contenuti (es. gli NPC) alle stanze corrette.
 */
public record Level(Room startingRoom, Map<String, Room> roomsById) {}