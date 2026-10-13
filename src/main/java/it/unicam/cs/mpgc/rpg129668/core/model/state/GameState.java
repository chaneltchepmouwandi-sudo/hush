package it.unicam.cs.mpgc.rpg129668.core.model.state;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Effect;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Lo stato dinamico di una partita: protagonista, posizione, inventario,
 * oggetti ancora presenti nelle stanze, conoscenze sbloccate, fiducia
 * verso gli NPC, effetti attivi e richieste già completate. Non
 * contiene la definizione del mondo (stanze, NPC), che è statica.
 */
public class GameState {

    private final Player player;
    private final Position position;
    private final Map<String, Item> inventory = new LinkedHashMap<>();
    private final Map<String, List<Item>> itemsByRoomId = new HashMap<>();
    private final Set<String> unlockedKnowledge = new HashSet<>();
    private final Map<String, Integer> trust = new HashMap<>();
    private final List<Effect> activeEffects = new ArrayList<>();
    private final Set<String> completedRequestIds = new HashSet<>();

    public GameState(Player player, Position position) {
        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public void addItem(Item item) {
        inventory.put(item.getId(), item);
    }

    public Optional<Item> removeItem(String itemId) {
        return Optional.ofNullable(inventory.remove(itemId));
    }

    public boolean hasItem(String itemId) {
        return inventory.containsKey(itemId);
    }

    public Optional<Item> getItem(String itemId) {
        return Optional.ofNullable(inventory.get(itemId));
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(inventory.values());
    }

    /**
     * Posiziona un oggetto in una stanza, perché il giocatore possa
     * raccoglierlo. Usato all'avvio della partita per i contenuti
     * caricati da content.
     */
    public void placeItem(Room room, Item item) {
        itemsByRoomId.computeIfAbsent(room.getId(), id -> new ArrayList<>()).add(item);
    }

    /**
     * @return gli oggetti ancora presenti nella stanza (non ancora raccolti)
     */
    public List<Item> getItemsInRoom(Room room) {
        return Collections.unmodifiableList(itemsByRoomId.getOrDefault(room.getId(), List.of()));
    }

    /**
     * Toglie un oggetto da una stanza (es. quando viene raccolto).
     */
    public Optional<Item> removeItemFromRoom(Room room, String itemId) {
        List<Item> items = itemsByRoomId.get(room.getId());
        if (items == null) {
            return Optional.empty();
        }
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getId().equals(itemId)) {
                iterator.remove();
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Registra una conoscenza. Un id null viene ignorato, perché un
     * Readable atmosferico può non sbloccare nulla.
     */
    public void unlockKnowledge(String knowledgeId) {
        if (knowledgeId != null) {
            unlockedKnowledge.add(knowledgeId);
        }
    }

    public boolean hasKnowledge(String knowledgeId) {
        return unlockedKnowledge.contains(knowledgeId);
    }

    public Set<String> getUnlockedKnowledge() {
        return Collections.unmodifiableSet(unlockedKnowledge);
    }

    /**
     * @return la fiducia dell'NPC verso la protagonista, 0 se non è mai cambiata
     */
    public int getTrust(String npcId) {
        return trust.getOrDefault(npcId, 0);
    }

    public void changeTrust(String npcId, int delta) {
        trust.merge(npcId, delta, Integer::sum);
    }

    /**
     * Applica subito l'effetto alle statistiche e lo tiene attivo
     * finché non scade.
     */
    public void addEffect(Effect effect) {
        effect.apply(player.getStats());
        activeEffects.add(effect);
    }

    public List<Effect> getActiveEffects() {
        return Collections.unmodifiableList(activeEffects);
    }

    /**
     * Fa passare un turno: gli effetti scaduti vengono rimossi e le
     * statistiche tornano com'erano.
     */
    public void tick() {
        Iterator<Effect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            Effect effect = iterator.next();
            effect.tick();
            if (effect.getRemainingTurns() <= 0) {
                effect.remove(player.getStats());
                iterator.remove();
            }
        }
    }

    /**
     * Segna una Request come completata. Chiamato da Request.tryFulfill,
     * non direttamente altrove.
     */
    public void markRequestCompleted(String requestId) {
        completedRequestIds.add(requestId);
    }

    public boolean hasCompletedRequest(String requestId) {
        return completedRequestIds.contains(requestId);
    }
}