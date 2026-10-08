package it.unicam.cs.mpgc.rpg129668.content.npc;

import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.interaction.Request;

import java.util.List;
import java.util.Map;

/**
 * Risultato del caricamento degli NPC: gli Npc stessi e le Request
 * associate, indicizzate per id dell'NPC a cui appartengono.
 */
public record NpcContent(List<Npc> npcs, Map<String, Request> requestsByNpcId) {}