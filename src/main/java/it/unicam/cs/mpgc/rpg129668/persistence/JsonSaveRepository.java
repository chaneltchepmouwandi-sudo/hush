package it.unicam.cs.mpgc.rpg129668.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Salva e carica lo stato di gioco come file JSON su disco.
 */
public class JsonSaveRepository implements SaveRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path saveFile;

    public JsonSaveRepository(Path saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public void save(GameSnapshot snapshot) {
        try {
            Files.writeString(saveFile, gson.toJson(snapshot));
        } catch (IOException e) {
            throw new IllegalStateException("Errore nel salvataggio su " + saveFile, e);
        }
    }

    @Override
    public GameSnapshot load() {
        try {
            String json = Files.readString(saveFile);
            return gson.fromJson(json, GameSnapshot.class);
        } catch (IOException e) {
            throw new IllegalStateException("Errore nel caricamento da " + saveFile, e);
        }
    }

    @Override
    public boolean hasSave() {
        return Files.exists(saveFile);
    }
}