package it.unicam.cs.mpgc.rpg129668.core.event;

/**
 * Un evento emesso dal motore di gioco, con un messaggio descrittivo
 * pensato per essere mostrato direttamente all'utente.
 */
public record GameEvent(EventType type, String message) {}