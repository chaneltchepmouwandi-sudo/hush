package it.unicam.cs.mpgc.rpg129668.core.event;

/**
 * Chi vuole essere notificato degli eventi di gioco.
 */
@FunctionalInterface
public interface GameEventListener {
    void onEvent(GameEvent event);
}