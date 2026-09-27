package it.unicam.cs.mpgc.rpg129668.core.model.character;

/**
 * Un personaggio non giocante (paziente o membro dello staff).
 * Per ora contiene solo identità e descrizione; le informazioni che
 * conosce e le condizioni per ottenerle saranno gestite da
 * core.model.interaction, non qui.
 */
public class Npc {

    private final String id;
    private final String name;
    private final String description;

    public Npc(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}