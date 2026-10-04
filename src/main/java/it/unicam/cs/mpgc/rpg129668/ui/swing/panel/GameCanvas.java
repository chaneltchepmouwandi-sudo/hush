package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.MoveAction;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import java.awt.event.HierarchyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Vista grafica della stanza corrente: disegna una pianta semplificata
 * e il personaggio come un cerchio che si muove con le frecce/WASD.
 * La posizione sullo schermo è puramente visiva: il modello di gioco
 * (GameState, Room) continua a ragionare solo per stanze, non per
 * coordinate.
 */
public class GameCanvas extends JPanel {

    private static final int PLAYER_SIZE = 20;
    private static final int STEP = 8;
    private static final int EXIT_ZONE = 30;

    private final GameController controller;
    private double playerX;
    private double playerY;
    private boolean initialized = false;

    public GameCanvas(GameController controller) {
        this.controller = controller;
        setBackground(new Color(30, 30, 30));
        setFocusable(true);

        controller.addListener(event -> repaint());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                requestFocusInWindow();
            }
        });
    }

    private void handleKey(int keyCode) {
        String direction = switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> "nord";
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> "sud";
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> "est";
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> "ovest";
            default -> null;
        };
        if (direction != null) {
            move(direction);
        }
    }

    private void move(String direction) {
        double newX = playerX;
        double newY = playerY;
        switch (direction) {
            case "nord" -> newY -= STEP;
            case "sud" -> newY += STEP;
            case "est" -> newX += STEP;
            case "ovest" -> newX -= STEP;
        }

        if (isBeyondEdge(direction, newX, newY)) {
            tryLeaveRoom(direction);
            return;
        }

        playerX = clamp(newX, 0, getWidth());
        playerY = clamp(newY, 0, getHeight());
        repaint();
    }

    private boolean isBeyondEdge(String direction, double x, double y) {
        return switch (direction) {
            case "nord" -> y < EXIT_ZONE;
            case "sud" -> y > getHeight() - EXIT_ZONE;
            case "est" -> x > getWidth() - EXIT_ZONE;
            case "ovest" -> x < EXIT_ZONE;
            default -> false;
        };
    }

    private void tryLeaveRoom(String direction) {
        Room before = controller.getState().getPosition().getCurrentRoom();
        controller.performAction(new MoveAction(direction));
        Room after = controller.getState().getPosition().getCurrentRoom();

        if (!after.equals(before)) {
            resetPlayerToOppositeSide(direction);
        }
    }

    private void resetPlayerToOppositeSide(String direction) {
        switch (direction) {
            case "nord" -> { playerX = getWidth() / 2.0; playerY = getHeight() - EXIT_ZONE - 5; }
            case "sud" -> { playerX = getWidth() / 2.0; playerY = EXIT_ZONE + 5; }
            case "est" -> { playerX = EXIT_ZONE + 5; playerY = getHeight() / 2.0; }
            case "ovest" -> { playerX = getWidth() - EXIT_ZONE - 5; playerY = getHeight() / 2.0; }
        }
        repaint();
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!initialized && getWidth() > 0 && getHeight() > 0) {
            playerX = getWidth() / 2.0;
            playerY = getHeight() / 2.0;
            initialized = true;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawRoomLabel(g2);
        drawExits(g2);
        drawPlayer(g2);
    }

    private void drawRoomLabel(Graphics2D g2) {
        Room room = controller.getState().getPosition().getCurrentRoom();
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
        g2.drawString(room.getName(), 10, 20);
    }

    private void drawExits(Graphics2D g2) {
        Room room = controller.getState().getPosition().getCurrentRoom();
        drawExitIfPresent(g2, room, "nord", getWidth() / 2 - 10, 15);
        drawExitIfPresent(g2, room, "sud", getWidth() / 2 - 10, getHeight() - 10);
        drawExitIfPresent(g2, room, "est", getWidth() - 25, getHeight() / 2);
        drawExitIfPresent(g2, room, "ovest", 10, getHeight() / 2);
    }

    private void drawExitIfPresent(Graphics2D g2, Room room, String direction, int x, int y) {
        room.getExit(direction).ifPresent(exit -> {
            g2.setColor(exit.isPassable() ? new Color(80, 200, 120) : new Color(200, 80, 80));
            g2.fillRect(x, y, 20, 6);
        });
    }

    private void drawPlayer(Graphics2D g2) {
        g2.setColor(new Color(90, 150, 230));
        g2.fillOval((int) playerX - PLAYER_SIZE / 2, (int) playerY - PLAYER_SIZE / 2, PLAYER_SIZE, PLAYER_SIZE);
    }
}