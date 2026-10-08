package it.unicam.cs.mpgc.rpg129668.controller;

import it.unicam.cs.mpgc.rpg129668.core.action.GameAction;
import it.unicam.cs.mpgc.rpg129668.core.event.EventType;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEventListener;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Guard;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.rule.CapturePolicy;
import it.unicam.cs.mpgc.rpg129668.core.rule.DetectionRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Orchestra un turno di gioco: esegue l'azione del giocatore, fa
 * avanzare effetti e guardie, verifica se la protagonista viene
 * scoperta, e notifica gli ascoltatori registrati tramite eventi.
 */
public class GameController {

    private final GameState state;
    private final List<Guard> guards;
    private final List<Npc> npcs;
    private final Map<String, Request> requestsByNpcId;
    private final DetectionRule detectionRule;
    private final CapturePolicy capturePolicy;
    private final List<GameEventListener> listeners = new ArrayList<>();

    public GameController(GameState state, List<Guard> guards, List<Npc> npcs,
                          Map<String, Request> requestsByNpcId,
                          DetectionRule detectionRule, CapturePolicy capturePolicy) {
        this.state = state;
        this.guards = guards;
        this.npcs = npcs;
        this.requestsByNpcId = requestsByNpcId;
        this.detectionRule = detectionRule;
        this.capturePolicy = capturePolicy;
    }

    public GameState getState() {
        return state;
    }

    /**
     * @return gli Npc posizionati nella stanza in cui si trova attualmente il giocatore
     */
    public List<Npc> getNpcsInCurrentRoom() {
        return npcs.stream()
                .filter(npc -> npc.getCurrentRoom() != null)
                .filter(npc -> npc.getCurrentRoom().equals(state.getPosition().getCurrentRoom()))
                .toList();
    }

    /**
     * @return la Request di questo Npc, o null se non ne ha una
     */
    public Request getRequestFor(Npc npc) {
        return requestsByNpcId.get(npc.getId());
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