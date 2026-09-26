package it.unicam.cs.mpgc.rpg129668.core.model.world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void startsInGivenRoom() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Position position = new Position(corridoio);

        assertEquals(corridoio, position.getCurrentRoom());
    }

    @Test
    void moveToChangesCurrentRoom() {
        Room corridoio = new Room("corridoio", "Corridoio", "Un lungo corridoio buio.");
        Room sala = new Room("sala", "Sala comune", "Pazienti seduti in silenzio.");
        Position position = new Position(corridoio);

        position.moveTo(sala);

        assertEquals(sala, position.getCurrentRoom());
    }
}