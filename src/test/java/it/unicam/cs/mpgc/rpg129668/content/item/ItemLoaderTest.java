package it.unicam.cs.mpgc.rpg129668.content.item;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemLoaderTest {

    @Test
    void groupsItemsByRoom() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        assertTrue(content.itemsByRoomId().containsKey("sala"));
        assertEquals(1, content.itemsByRoomId().get("sala").size());
    }

    @Test
    void createsTheCorrectConcreteType() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        Item key = content.itemsByRoomId().get("corridoio").get(0);
        assertInstanceOf(Key.class, key);
    }

    @Test
    void indexesItemsById() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        assertTrue(content.itemsById().containsKey("peluche"));
        assertTrue(content.itemsById().containsKey("chiave-ufficio"));
    }
}