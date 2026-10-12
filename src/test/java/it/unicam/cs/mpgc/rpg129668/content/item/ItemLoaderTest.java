package it.unicam.cs.mpgc.rpg129668.content.item;

import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemLoaderTest {

    @Test
    void groupsItemsByRoom() {
        Map<String, List<Item>> itemsByRoom = new ItemLoader().load("/content/items.json");

        assertTrue(itemsByRoom.containsKey("sala"));
        assertEquals(1, itemsByRoom.get("sala").size());
    }

    @Test
    void createsTheCorrectConcreteType() {
        Map<String, List<Item>> itemsByRoom = new ItemLoader().load("/content/items.json");

        Item key = itemsByRoom.get("corridoio").get(0);
        assertInstanceOf(Key.class, key);
    }
}