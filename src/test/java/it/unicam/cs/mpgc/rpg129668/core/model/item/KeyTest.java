package it.unicam.cs.mpgc.rpg129668.core.model.item;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Door;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyTest {

    @Test
    void exposesItsUnlockCode() {
        Key key = new Key("chiave-ufficio", "Chiave dell'ufficio", "Una vecchia chiave di ottone",
                "chiave-ufficio");

        assertEquals("chiave-ufficio", key.getUnlockCode());
    }

    @Test
    void unlockCodeMatchesCorrespondingDoor() {
        Key key = new Key("chiave-ufficio", "Chiave dell'ufficio", "Una vecchia chiave di ottone",
                "chiave-ufficio");
        Door door = new Door("chiave-ufficio");

        boolean opened = door.tryResolve(key.getUnlockCode());

        assertTrue(opened);
    }

    @Test
    void exposesItsIdentity() {
        Key key = new Key("chiave-ufficio", "Chiave dell'ufficio", "Una vecchia chiave di ottone",
                "chiave-ufficio");

        assertEquals("chiave-ufficio", key.getId());
        assertEquals("Chiave dell'ufficio", key.getName());
    }
}

