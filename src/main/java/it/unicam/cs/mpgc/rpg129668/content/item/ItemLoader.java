package it.unicam.cs.mpgc.rpg129668.content.item;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Carica gli oggetti raccoglibili da un file JSON, raggruppati per
 * stanza e indicizzati per id.
 *
 * TODO: supportare anche "pill" e "document" quando servirà
 *       posizionarne nel mondo (per ora costruiti a mano nei test)
 */
public class ItemLoader {

    private final Gson gson = new Gson();

    public ItemContent load(String resourcePath) {
        ItemListData data = readItemListData(resourcePath);
        Map<String, List<Item>> itemsByRoomId = new HashMap<>();
        Map<String, Item> itemsById = new HashMap<>();

        for (ItemData itemData : data.items) {
            Item item = createItem(itemData);
            itemsByRoomId.computeIfAbsent(itemData.roomId, id -> new ArrayList<>()).add(item);
            itemsById.put(item.getId(), item);
        }

        return new ItemContent(itemsByRoomId, itemsById);
    }

    private ItemListData readItemListData(String resourcePath) {
        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Risorsa non trovata: " + resourcePath);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            return gson.fromJson(reader, ItemListData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Errore nella lettura di " + resourcePath, e);
        }
    }

    private Item createItem(ItemData data) {
        return switch (data.type) {
            case "generic" -> new GenericItem(data.id, data.name, data.description);
            case "key" -> new Key(data.id, data.name, data.description, data.unlockCode);
            default -> throw new IllegalArgumentException("Tipo di oggetto sconosciuto: " + data.type);
        };
    }
}