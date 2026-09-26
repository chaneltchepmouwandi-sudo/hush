package it.unicam.cs.mpgc.rpg129668.core.model.world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoorTest {

    @Test
    void isClosedByDefault() {
        Door door = new Door("chiave-ufficio");
        assertFalse(door.isOpen());
    }

    @Test
    void wrongCodeKeepsDoorClosed() {
        Door door = new Door("chiave-ufficio");
        door.tryResolve("chiave-sbagliata");
        assertFalse(door.isOpen());
    }

    @Test
    void correctCodeOpensDoor() {
        Door door = new Door("chiave-ufficio");
        boolean result = door.tryResolve("chiave-ufficio");

        assertTrue(result);
        assertTrue(door.isOpen());
    }

    @Test
    void doorStaysOpenAfterBeingOpened() {
        Door door = new Door("chiave-ufficio");
        door.tryResolve("chiave-ufficio");
        door.tryResolve("qualcos-altro");

        assertTrue(door.isOpen());
    }
}