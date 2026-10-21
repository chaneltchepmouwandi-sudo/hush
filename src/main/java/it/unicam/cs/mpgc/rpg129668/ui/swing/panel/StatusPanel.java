package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Stat;
import it.unicam.cs.mpgc.rpg129668.core.model.character.StatBlock;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.state.GameState;
import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Pannello laterale che mostra lo stato della protagonista: statistiche
 * (valore effettivo e modificatori temporanei), fiducia degli NPC che
 * hanno già interagito con lei, e inventario. Legge sempre lo stato
 * corrente da GameController e si ridisegna a ogni evento, senza
 * tenere copie proprie dei dati.
 */
public class StatusPanel extends JPanel {

    private final GameController controller;

    public StatusPanel(GameController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UiTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        setPreferredSize(new Dimension(210, 0));

        controller.addListener(event -> refresh());
        refresh();
    }

    private void refresh() {
        removeAll();
        GameState state = controller.getState();

        addSection("STATISTICHE");
        StatBlock stats = state.getPlayer().getStats();
        for (Stat stat : Stat.values()) {
            addLine(statLine(stat, stats), UiTheme.TEXT_PRIMARY);
        }

        addSection("FIDUCIA");
        boolean anyTrust = false;
        for (Npc npc : controller.getNpcs()) {
            int trust = state.getTrust(npc.getId());
            if (trust != 0) {
                addLine(npc.getName() + ": " + trust, UiTheme.TEXT_PRIMARY);
                anyTrust = true;
            }
        }
        if (!anyTrust) {
            addLine("Nessuna per ora", UiTheme.TEXT_SECONDARY);
        }

        addSection("INVENTARIO");
        Collection<Item> items = state.getItems();
        if (items.isEmpty()) {
            addLine("Vuoto", UiTheme.TEXT_SECONDARY);
        }
        for (Item item : items) {
            addLine(item.getName(), UiTheme.TEXT_PRIMARY);
        }

        revalidate();
        repaint();
    }

    /**
     * Mostra il valore effettivo e, se presente, il modificatore
     * temporaneo tra parentesi (es. "Carisma: 5 (+2)").
     */
    private String statLine(Stat stat, StatBlock stats) {
        int effective = stats.getEffectiveValue(stat);
        int modifier = effective - stats.getBase(stat);
        String line = label(stat) + ": " + effective;
        if (modifier != 0) {
            line += " (" + (modifier > 0 ? "+" : "") + modifier + ")";
        }
        return line;
    }

    private String label(Stat stat) {
        return switch (stat) {
            case CARISMA -> "Carisma";
            case FORZA -> "Forza";
            case PERCEZIONE -> "Percezione";
            case AGILITA -> "Agilità";
        };
    }

    private void addSection(String title) {
        if (getComponentCount() > 0) {
            add(Box.createVerticalStrut(16));
        }
        JLabel label = new JLabel(title);
        label.setForeground(UiTheme.ACCENT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        add(Box.createVerticalStrut(6));
    }

    private void addLine(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 14f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        add(Box.createVerticalStrut(3));
    }
}