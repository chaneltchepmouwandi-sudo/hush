package it.unicam.cs.mpgc.rpg129668.content.item;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Pill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemLoaderTest {

    @Test
    void groupsItemsByRoom() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        assertTrue(content.itemsByRoomId().containsKey("sala"));
        assertEquals(2, content.itemsByRoomId().get("sala").size()); // peluche e pillola azzurra
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

    @Test
    void loadsPillWithStatModifierEffect() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        Item item = content.itemsById().get("pillola-azzurra");
        assertInstanceOf(Pill.class, item);

        StatBlock stats = new StatBlock();
        stats.setBase(Stat.CARISMA, 2);
        ((Pill) item).getEffect().apply(stats);

        assertEquals(4, stats.getEffectiveValue(Stat.CARISMA));
    }

    @Test
    void loadsPillWithConfusionEffect() {
        ItemContent content = new ItemLoader().load("/content/items.json");
        Item item = content.itemsById().get("pillola-grigia");
        assertInstanceOf(Pill.class, item);

        StatBlock stats = new StatBlock();
        stats.setBase(Stat.PERCEZIONE, 3);
        stats.setBase(Stat.AGILITA, 3);
        ((Pill) item).getEffect().apply(stats);

        assertEquals(1, stats.getEffectiveValue(Stat.PERCEZIONE));
        assertEquals(2, stats.getEffectiveValue(Stat.AGILITA));
    }
}