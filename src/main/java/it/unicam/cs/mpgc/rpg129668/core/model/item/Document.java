package it.unicam.cs.mpgc.rpg129668.core.model.item;

/**
 * Un documento o foglio trovato nell'ambiente, raccoglibile come Item
 * e leggibile per sbloccare una conoscenza.
 */
public class Document implements Item, Readable {

    private final String id;
    private final String title;
    private final String content;
    private final String unlockedKnowledgeId;

    public Document(String id, String title, String content, String unlockedKnowledgeId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.unlockedKnowledgeId = unlockedKnowledgeId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getDescription() {
        return "Un documento: " + title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getUnlockedKnowledgeId() {
        return unlockedKnowledgeId;
    }
}