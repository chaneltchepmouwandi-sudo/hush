package it.unicam.cs.mpgc.rpg129668.core.model.item;

/**
 * Un oggetto raccoglibile senza alcun comportamento proprio, usato
 * tipicamente come oggetto da consegnare per soddisfare una Request
 * (es. un ricordo personale).
 */
public class GenericItem implements Item {

    private final String id;
    private final String name;
    private final String description;

    public GenericItem(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}