package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.TalkAction;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello di gioco: vista grafica della stanza (GameCanvas), elenco
 * degli NPC presenti con cui parlare, e un log testuale degli eventi.
 *
 * TODO: disegnare gli NPC anche su GameCanvas, non solo come pulsanti
 */
public class GamePanel extends JPanel {

    private final GameController controller;
    private final GameCanvas canvas;
    private final JPanel npcPanel = new JPanel();
    private final JTextArea logArea = new JTextArea();

    public GamePanel(GameController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        canvas = new GameCanvas(controller);
        add(canvas, BorderLayout.CENTER);

        npcPanel.setLayout(new FlowLayout());
        add(npcPanel, BorderLayout.NORTH);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setRows(4);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        controller.addListener(this::onGameEvent);

        updateNpcPanel();
    }

    private void onGameEvent(GameEvent event) {
        logArea.append(event.message() + "\n");
        updateNpcPanel();
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

    public void focusCanvas() {
        canvas.requestFocusInWindow();
    }
}