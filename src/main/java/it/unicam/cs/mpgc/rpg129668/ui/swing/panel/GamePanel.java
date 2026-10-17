package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.PickUpAction;
import it.unicam.cs.mpgc.rpg129668.core.action.TalkAction;
import it.unicam.cs.mpgc.rpg129668.core.action.TryUnlockAction;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello di gioco: vista grafica della stanza (GameCanvas), elenco
 * degli NPC presenti con cui parlare, elenco degli oggetti raccoglibili
 * nella stanza, pulsanti per aprire i passaggi bloccati, un pulsante
 * per salvare, e un log testuale degli eventi.
 */
public class GamePanel extends JPanel {

    private static final List<String> DIRECTIONS = List.of("nord", "sud", "est", "ovest");

    private final GameController controller;
    private final GameCanvas canvas;
    private final JPanel npcPanel = new JPanel(new FlowLayout());
    private final JPanel itemPanel = new JPanel(new FlowLayout());
    private final JPanel openPanel = new JPanel(new FlowLayout());
    private final JTextArea logArea = new JTextArea();

    public GamePanel(GameController controller, Runnable onSave) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(UiTheme.BACKGROUND);

        canvas = new GameCanvas(controller);
        add(canvas, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(UiTheme.BACKGROUND);
        npcPanel.setBackground(UiTheme.BACKGROUND);
        itemPanel.setBackground(UiTheme.BACKGROUND);
        openPanel.setBackground(UiTheme.BACKGROUND);
        topPanel.add(npcPanel);
        topPanel.add(itemPanel);
        topPanel.add(openPanel);
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
        UiTheme.styleButton(saveButton);
        bottomPanel.add(saveButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        controller.addListener(this::onGameEvent);

        updateActionPanels();
    }

    private void onGameEvent(GameEvent event) {
        logArea.append(event.message() + "\n");
        updateActionPanels();
    }

    private void updateActionPanels() {
        updateNpcPanel();
        updateItemPanel();
        updateOpenPanel();
    }

    private void updateNpcPanel() {
        npcPanel.removeAll();
        for (Npc npc : controller.getNpcsInCurrentRoom()) {
            JButton button = new JButton("Parla con " + npc.getName());
            button.addActionListener(e -> controller.performAction(
                    new TalkAction(npc, controller.getRequestFor(npc))));
            UiTheme.styleButton(button);
            npcPanel.add(button);
        }
        npcPanel.revalidate();
        npcPanel.repaint();
    }

    private void updateItemPanel() {
        itemPanel.removeAll();
        for (Item item : controller.getItemsInCurrentRoom()) {
            JButton button = new JButton("Raccogli " + item.getName());
            button.addActionListener(e -> controller.performAction(new PickUpAction(item.getId())));
            UiTheme.styleButton(button);
            itemPanel.add(button);
        }
        itemPanel.revalidate();
        itemPanel.repaint();
    }

    private void updateOpenPanel() {
        openPanel.removeAll();
        Room room = controller.getState().getPosition().getCurrentRoom();
        for (String direction : DIRECTIONS) {
            room.getExit(direction).ifPresent(exit -> {
                if (!exit.isPassable()) {
                    JButton button = new JButton("Apri verso " + direction);
                    button.addActionListener(e -> controller.performAction(new TryUnlockAction(direction)));
                    UiTheme.styleButton(button);
                    openPanel.add(button);
                }
            });
        }
        openPanel.revalidate();
        openPanel.repaint();
    }

    public void focusCanvas() {
        canvas.requestFocusInWindow();
    }
}