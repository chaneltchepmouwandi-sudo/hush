package it.unicam.cs.mpgc.rpg129668.content.npc;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Carica gli NPC (e le eventuali Request che offrono) da un file JSON.
 */
public class NpcLoader {

    private final Gson gson = new Gson();

    public List<Npc> load(String resourcePath) {
        NpcListData data = readNpcListData(resourcePath);
        List<Npc> npcs = new ArrayList<>();
        for (NpcData npcData : data.npcs) {
            npcs.add(new Npc(npcData.id, npcData.name, npcData.description));
        }
        return npcs;
    }

    /**
     * Carica la Request associata a un NPC, se presente nel file.
     * Separata da load() perché Request appartiene a GameController/UI,
     * non a Npc stesso (vedi core.model.character.Npc).
     */
    public List<Request> loadRequests(String resourcePath) {
        NpcListData data = readNpcListData(resourcePath);
        List<Request> requests = new ArrayList<>();
        for (NpcData npcData : data.npcs) {
            if (npcData.request != null) {
                requests.add(createRequest(npcData.request));
            }
        }
        return requests;
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