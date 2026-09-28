package it.unicam.cs.mpgc.rpg129668.core.model.character;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import java.util.List;

/**
 * Muove la guardia lungo un percorso fisso di stanze, ricominciando
 * da capo una volta raggiunta l'ultima (es. A, B, C, A, B, C...).
 */
public class PatrolBehavior implements Behavior {

    private final List<Room> route;
    private int currentIndex;

    public PatrolBehavior(List<Room> route) {
        if (route.isEmpty()) {
            throw new IllegalArgumentException("Il percorso di pattugliamento non può essere vuoto");
        }
        this.route = route;
        this.currentIndex = 0;
    }

    @Override
    public void update(Guard guard) {
        currentIndex = (currentIndex + 1) % route.size();
        guard.moveTo(route.get(currentIndex));
    }
}