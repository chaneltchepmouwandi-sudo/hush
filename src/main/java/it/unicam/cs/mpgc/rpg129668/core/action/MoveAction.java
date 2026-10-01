package it.unicam.cs.mpgc.rpg129668.core.action;

import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import java.util.Optional;

/**
 * Sposta il giocatore in una direzione, se esiste un'uscita ed è libera.
 */
public class MoveAction implements GameAction {

    private final String direction;

    public MoveAction(String direction) {
        this.direction = direction;
    }

    @Override
    public String execute(GameState state) {
        Room currentRoom = state.getPosition().getCurrentRoom();
        Optional<Room.Exit> exit = currentRoom.getExit(direction);

        if (exit.isEmpty()) {
            return "Non c'è nessuna uscita in quella direzione.";
        }
        if (!exit.get().isPassable()) {
            return "Il passaggio è bloccato.";
        }

        Room destination = exit.get().destination();
        state.getPosition().moveTo(destination);
        return "Ti sei spostata in " + destination.getName() + ".";
    }
}