package it.unicam.cs.mpgc.rpg129668.core.model.item;

/**
 * Un contenuto leggibile che, una volta letto, sblocca una conoscenza
 * identificata da un id, usata più avanti per condizionare dialoghi
 * e indizi. L'id può essere null se il documento è puramente
 * atmosferico e non sblocca nulla.
 */
public interface Readable {

    String getTitle();

    String getContent();

    String getUnlockedKnowledgeId();
}