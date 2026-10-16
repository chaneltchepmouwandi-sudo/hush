package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.PickUpAction;
import it.unicam.cs.mpgc.rpg129668.core.action.TalkAction;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello di gioco: vista grafica della stanza (GameCanvas), elenco
 * degli NPC presenti con cui parlare, elenco degli oggetti raccoglibili
 * nella stanza, un pulsante per salvare, e un log testuale degli eventi.
 *
 * TODO: disegnare NPC e oggetti anche su GameCanvas, non solo come pulsanti
 */
public class GamePanel extends JPanel {

    private final GameController controller;
    private final GameCanvas canvas;
    private final JPanel npcPanel = new JPanel(new FlowLayout());
    private final JPanel itemPanel = new JPanel(new FlowLayout());
    private final JTextArea logArea = new JTextArea();

    public GamePanel(GameController controller, Runnable onSave) {
        this.controller = controller;
        setLayout(new BorderLayout());

        canvas = new GameCanvas(controller);
        add(canvas, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(npcPanel);
        topPanel.add(itemPanel);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setRows(4);
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        JButton saveButton = new JButton("Salva partita");
        saveButton.addActionListener(e -> {
            onSave.run();
            logArea.append("Partita salvata.\n");
        });
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
    }

    private void updateNpcPanel() {
        npcPanel.removeAll();
        for (Npc npc : controller.getNpcsInCurrentRoom()) {
            JButton button = new JButton("Parla con " + npc.getName());
            button.addActionListener(e -> controller.performAction(
                    new TalkAction(npc, controller.getRequestFor(npc))));
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
            itemPanel.add(button);
        }
        itemPanel.revalidate();
        itemPanel.repaint();
    }

    public void focusCanvas() {
        canvas.requestFocusInWindow();
    }
}