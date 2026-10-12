package it.unicam.cs.mpgc.rpg129668.core.model.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericItemTest {

    @Test
    void exposesItsIdentity() {
        GenericItem peluche = new GenericItem("peluche", "Peluche", "Un peluche consumato dal tempo.");

        assertEquals("peluche", peluche.getId());
        assertEquals("Peluche", peluche.getName());
        assertEquals("Un peluche consumato dal tempo.", peluche.getDescription());
    }
}