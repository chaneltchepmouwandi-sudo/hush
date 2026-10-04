package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.event.GameEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello di gioco: mostra la vista grafica della stanza corrente
 * (GameCanvas) e un log testuale degli eventi. Il movimento avviene
 * con le frecce/WASD, gestite direttamente da GameCanvas.
 */
public class GamePanel extends JPanel {

    private final GameCanvas canvas;
    private final JTextArea logArea = new JTextArea();

    public GamePanel(GameController controller) {
        setLayout(new BorderLayout());

        canvas = new GameCanvas(controller);
        add(canvas, BorderLayout.CENTER);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setRows(4);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        controller.addListener(this::onGameEvent);
    }

    private void onGameEvent(GameEvent event) {
        logArea.append(event.message() + "\n");
    }

    public void focusCanvas() {
        canvas.requestFocusInWindow();
    }
}