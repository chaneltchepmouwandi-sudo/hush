package it.unicam.cs.mpgc.rpg129668.core.model.character;

import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NpcTest {

    @Test
    void exposesBasicInfo() {
        Npc elio = new Npc("elio", "Elio",
                "Un bambino silenzioso che scarabocchia sempre lo stesso disegno.");

        assertEquals("Elio", elio.getName());
        assertEquals("elio", elio.getId());
    }

    @Test
    void canBePlacedInARoom() {
        Npc elio = new Npc("elio", "Elio", "...");
        Room sala = new Room("sala", "Sala comune", "...");

        elio.placeIn(sala);

        assertEquals(sala, elio.getCurrentRoom());
    }
}