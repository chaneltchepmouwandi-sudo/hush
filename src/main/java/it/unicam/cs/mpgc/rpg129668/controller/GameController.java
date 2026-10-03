package it.unicam.cs.mpgc.rpg129668.controller;

import it.unicam.cs.mpgc.rpg129668.core.action.GameAction;
import it.unicam.cs.mpgc.rpg129668.core.event.EventType;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEventListener;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.rule.CapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.DetectionRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestra un turno di gioco: esegue l'azione del giocatore, fa
 * avanzare effetti e guardie, verifica se la protagonista viene
 * scoperta, e notifica gli ascoltatori registrati tramite eventi.
 */
public class GameController {

    private final GameState state;
    private final List<Guard> guards;
    private final DetectionRule detectionRule;
    private final CapturePolicy capturePolicy;
    private final List<GameEventListener> listeners = new ArrayList<>();

    public GameController(GameState state, List<Guard> guards,
                          DetectionRule detectionRule, CapturePolicy capturePolicy) {
        this.state = state;
        this.guards = guards;
        this.detectionRule = detectionRule;
        this.capturePolicy = capturePolicy;
    }

    public GameState getState() {
        return state;
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Esegue l'azione del giocatore, notifica il risultato, poi fa
     * avanzare il turno (effetti, guardie, controllo di cattura).
     */
    public void performAction(GameAction action) {
        String result = action.execute(state);
        notify(new GameEvent(EventType.TURN_RESULT, result));
        advanceTurn();
    }

    private void advanceTurn() {
        state.tick();
        guards.forEach(Guard::tick);
        checkDetection();
    }

    private void checkDetection() {
        for (Guard guard : guards) {
            if (detectionRule.detects(guard, state)) {
                String message = capturePolicy.apply(state);
                notify(new GameEvent(EventType.PLAYER_CAPTURED, message));
                return;
            }
        }
    }

    private void notify(GameEvent event) {
        listeners.forEach(listener -> listener.onEvent(event));
    }
}