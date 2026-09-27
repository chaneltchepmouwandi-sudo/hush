package it.unicam.cs.mpgc.rpg129668.core.model.item;

import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.effect.Effect;

/**
 * Una pillola che, se usata, applica un Effect a uno StatBlock.
 * Il tipo di effetto (aumento di una statistica, confusione, ...)
 * è deciso da chi la crea, non da questa classe.
 */
public class Pill implements Item {

    private final String id;
    private final String name;
    private final String description;
    private final Effect effect;

    public Pill(String id, String name, String description, Effect effect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.effect = effect;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void use(StatBlock stats) {
        effect.apply(stats);
    }

    public Effect getEffect() {
        return effect;
    }
}