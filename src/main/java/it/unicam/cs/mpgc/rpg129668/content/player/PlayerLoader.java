package it.unicam.cs.mpgc.rpg129668.content.player;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Player;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Carica la protagonista (nome e statistiche iniziali) da un file JSON.
 * Ogni chiamata a load() crea una nuova Player: va richiamato a ogni
 * nuova partita, perché le statistiche cambiano durante il gioco.
 */
public class PlayerLoader {

    private final Gson gson = new Gson();

    /**
     * @param resourcePath percorso del file JSON nelle risorse (es. "/content/player.json")
     * @return una nuova Player con il nome e le statistiche di base indicati nel file
     */
    public Player load(String resourcePath) {
        PlayerData data = readPlayerData(resourcePath);

        StatBlock stats = new StatBlock();
        if (data.stats != null) {
            data.stats.forEach((statName, value) -> stats.setBase(Stat.valueOf(statName), value));
        }
        return new Player(data.name, stats);
    }

    private PlayerData readPlayerData(String resourcePath) {
        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Risorsa non trovata: " + resourcePath);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            return gson.fromJson(reader, PlayerData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Errore nella lettura di " + resourcePath, e);
        }
    }
}