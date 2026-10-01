package it.unicam.cs.mpgc.rpg129668.core.model.state;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Effect;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Position;

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
 * conoscenze sbloccate, fiducia verso gli NPC ed effetti attivi.
 * Non contiene la definizione del mondo (stanze, NPC), che è statica.
 */
public class GameState {

    private final Player player;
    private final Position position;
    private final Map<String, Item> inventory = new LinkedHashMap<>();
    private final Set<String> unlockedKnowledge = new HashSet<>();
    private final Map<String, Integer> trust = new HashMap<>();
    private final List<Effect> activeEffects = new ArrayList<>();

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
}