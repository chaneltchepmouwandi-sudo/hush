package it.unicam.cs.mpgc.rpg129668.content.npc;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.*;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Carica gli NPC e le eventuali Request che offrono da un file JSON.
 */
public class NpcLoader {

    private final Gson gson = new Gson();

    /**
     * @param resourcePath percorso del file JSON nelle risorse
     * @param roomsById stanze del livello, usate per posizionare ogni NPC nella sua roomId
     * @return gli Npc (posizionati nella stanza corrispondente) e le Request,
     *         indicizzate per id dell'NPC a cui appartengono
     */
    public NpcContent load(String resourcePath, Map<String, Room> roomsById) {
        NpcListData data = readNpcListData(resourcePath);
        List<Npc> npcs = new ArrayList<>();
        Map<String, Request> requestsByNpcId = new HashMap<>();

        for (NpcData npcData : data.npcs) {
            Npc npc = new Npc(npcData.id, npcData.name, npcData.description);
            Room room = roomsById.get(npcData.roomId);
            if (room != null) {
                npc.placeIn(room);
            }
            npcs.add(npc);

            if (npcData.request != null) {
                requestsByNpcId.put(npcData.id, createRequest(npcData.request));
            }
        }

        return new NpcContent(npcs, requestsByNpcId);
    }

    private NpcListData readNpcListData(String resourcePath) {
        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Risorsa non trovata: " + resourcePath);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            return gson.fromJson(reader, NpcListData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Errore nella lettura di " + resourcePath, e);
        }
    }

    private Request createRequest(RequestData data) {
        Condition condition = createCondition(data.condition);
        List<Reward> rewards = data.rewards.stream().map(this::createReward).toList();
        return new Request(data.id, condition, rewards);
    }

    private Condition createCondition(ConditionData data) {
        return switch (data.type) {
            case "hasItem" -> new HasItemCondition(data.itemId);
            default -> throw new IllegalArgumentException("Tipo di condizione sconosciuto: " + data.type);
        };
    }

    private Reward createReward(RewardData data) {
        return switch (data.type) {
            case "takeItem" -> new TakeItemReward(data.itemId);
            case "trust" -> new TrustReward(data.npcId, data.amount);
            case "knowledge" -> new KnowledgeReward(data.knowledgeId);
            default -> throw new IllegalArgumentException("Tipo di ricompensa sconosciuto: " + data.type);
        };
    }
}