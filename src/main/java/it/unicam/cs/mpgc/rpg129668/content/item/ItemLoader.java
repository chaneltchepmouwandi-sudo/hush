package it.unicam.cs.mpgc.rpg129668.content.item;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Confusion;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Effect;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.StatModifier;
import it.unicam.cs.mpgc.rpg129668.core.model.item.GenericItem;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Key;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Pill;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Carica gli oggetti raccoglibili da un file JSON, raggruppati per
 * stanza e indicizzati per id.
 *
 * TODO: supportare anche "document" quando servirà posizionarne nel
 *       mondo (per ora costruiti a mano nei test)
 * TODO: gli Effect delle pillole hanno stato (turni rimanenti) e sono
 *       condivisi tra le partite avviate nella stessa esecuzione: se
 *       si aggiungerà il ritorno al menu, crearne uno nuovo a ogni
 *       nuova partita
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
            case "pill" -> new Pill(data.id, data.name, data.description, createEffect(data));
            default -> throw new IllegalArgumentException("Tipo di oggetto sconosciuto: " + data.type);
        };
    }

    private Effect createEffect(ItemData itemData) {
        EffectData data = itemData.effect;
        if (data == null) {
            throw new IllegalArgumentException("La pillola " + itemData.id + " non ha un effetto");
        }
        return switch (data.type) {
            case "statModifier" -> new StatModifier(Stat.valueOf(data.stat), data.amount, data.duration);
            case "confusion" -> new Confusion(createPenalties(data.penalties), data.duration);
            default -> throw new IllegalArgumentException("Tipo di effetto sconosciuto: " + data.type);
        };
    }

    private Map<Stat, Integer> createPenalties(List<PenaltyData> penalties) {
        Map<Stat, Integer> result = new EnumMap<>(Stat.class);
        for (PenaltyData penalty : penalties) {
            result.put(Stat.valueOf(penalty.stat), penalty.amount);
        }
        return result;
    }
}