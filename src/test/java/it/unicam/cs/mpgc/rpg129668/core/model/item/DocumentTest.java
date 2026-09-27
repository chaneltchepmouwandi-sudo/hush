package it.unicam.cs.mpgc.rpg129668.core.model.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentTest {

    @Test
    void exposesTitleAndUnlockedKnowledge() {
        Document document = new Document(
                "foglio-elio",
                "Un disegno stropicciato",
                "Sul retro, scritto in stampatello: \"LUCE ACCESA TUTTA LA NOTTE\".",
                "sa-luce-corridoio-nord"
        );

        assertEquals("Un disegno stropicciato", document.getTitle());
        assertEquals("sa-luce-corridoio-nord", document.getUnlockedKnowledgeId());
    }

    @Test
    void isAlsoUsableAsAGenericItem() {
        Document document = new Document(
                "foglio-elio",
                "Un disegno stropicciato",
                "...",
                "sa-luce-corridoio-nord"
        );

        assertEquals("foglio-elio", document.getId());
        assertEquals("Un disegno stropicciato", document.getName());
    }
}