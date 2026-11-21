package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.GameAction;
import it.unicam.cs.mpgc.rpg129668.core.action.PickUpAction;
import it.unicam.cs.mpgc.rpg129668.core.action.ReadAction;
import it.unicam.cs.mpgc.rpg129668.core.action.TalkAction;
import it.unicam.cs.mpgc.rpg129668.core.action.TryUnlockAction;
import it.unicam.cs.mpgc.rpg129668.core.action.UsePillAction;
import it.unicam.cs.mpgc.rpg129668.core.event.EventType;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Pill;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Readable;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello di gioco: vista grafica della stanza (GameCanvas), pannello
 * di stato laterale (StatusPanel), pulsanti per parlare con gli NPC
 * presenti, raccogliere gli oggetti della stanza, aprire i passaggi
 * bloccati, usare le pillole e leggere i documenti in inventario, un
 * pulsante per salvare, e un log testuale degli eventi.
 */
public class GamePanel extends JPanel {

    private static final List<String> DIRECTIONS = List.of("nord", "sud", "est", "ovest");

    private final GameController controller;
    private final GameCanvas canvas;
    private final JPanel npcPanel = new JPanel(new FlowLayout());
    private final JPanel itemPanel = new JPanel(new FlowLayout());
    private final JPanel openPanel = new JPanel(new FlowLayout());
    private final JPanel inventoryPanel = new JPanel(new FlowLayout());
    private final JTextArea logArea = new JTextArea();

    public GamePanel(GameController controller, Runnable onSave) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(UiTheme.BACKGROUND);

        canvas = new GameCanvas(controller);
        add(canvas, BorderLayout.CENTER);
        add(new StatusPanel(controller), BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(UiTheme.BACKGROUND);
        for (JPanel panel : List.of(npcPanel, itemPanel, openPanel, inventoryPanel)) {
            panel.setBackground(UiTheme.BACKGROUND);
            topPanel.add(panel);
        }
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UiTheme.BACKGROUND);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setRows(4);
        logArea.setBackground(UiTheme.SURFACE);
        logArea.setForeground(UiTheme.TEXT_PRIMARY);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.getViewport().setBackground(UiTheme.SURFACE);
        bottomPanel.add(logScroll, BorderLayout.CENTER);

        JButton saveButton = new JButton("Salva partita");
        saveButton.addActionListener(e -> {
            onSave.run();
            logArea.append("Partita salvata.\n");
        });
        saveButton.setFocusable(false);
        UiTheme.styleButton(saveButton);
        bottomPanel.add(saveButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        controller.addListener(this::onGameEvent);

        updateActionPanels();
    }

    private void onGameEvent(GameEvent event) {
        if (event.type() != EventType.TURN_ENDED) {
            logArea.append(event.message() + "\n");
        }
        updateActionPanels();
    }

    private void updateActionPanels() {
        updateNpcPanel();
        updateItemPanel();
        updateOpenPanel();
        updateInventoryPanel();
    }

    private void updateNpcPanel() {
        npcPanel.removeAll();
        for (Npc npc : controller.getNpcsInCurrentRoom()) {
            npcPanel.add(createActionButton("Parla con " + npc.getName(),
                    new TalkAction(npc, controller.getRequestFor(npc))));
        }
        refresh(npcPanel);
    }

    private void updateItemPanel() {
        itemPanel.removeAll();
        for (Item item : controller.getItemsInCurrentRoom()) {
            itemPanel.add(createActionButton("Raccogli " + item.getName(), new PickUpAction(item.getId())));
        }
        refresh(itemPanel);
    }

    private void updateOpenPanel() {
        openPanel.removeAll();
        Room room = controller.getState().getPosition().getCurrentRoom();
        for (String direction : DIRECTIONS) {
            room.getExit(direction).ifPresent(exit -> {
                if (!exit.isPassable()) {
                    openPanel.add(createActionButton("Apri verso " + direction, new TryUnlockAction(direction)));
                }
            });
        }
        refresh(openPanel);
    }

    private void updateInventoryPanel() {
        inventoryPanel.removeAll();
        for (Item item : controller.getState().getItems()) {
            if (item instanceof Pill) {
                inventoryPanel.add(createActionButton("Usa " + item.getName(), new UsePillAction(item.getId())));
            }
            if (item instanceof Readable) {
                inventoryPanel.add(createActionButton("Leggi " + item.getName(), new ReadAction(item.getId())));
            }
        }
        refresh(inventoryPanel);
    }

    /**
     * Crea un pulsante che esegue l'azione tramite il controller. Il
     * pulsante non prende il focus, così la tastiera resta sulla vista
     * della stanza e le frecce continuano a muovere il personaggio.
     */
    private JButton createActionButton(String label, GameAction action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> controller.performAction(action));
        button.setFocusable(false);
        UiTheme.styleButton(button);
        return button;
    }

    private void refresh(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public void focusCanvas() {
        canvas.requestFocusInWindow();
    }
}