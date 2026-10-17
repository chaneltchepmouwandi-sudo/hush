package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.core.action.MoveAction;
import it.unicam.cs.mpgc.rpg129668.core.model.character.Npc;
import it.unicam.cs.mpgc.rpg129668.core.model.item.Item;
import it.unicam.cs.mpgc.rpg129668.core.model.world.Room;
import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Vista grafica della stanza corrente: disegna una pianta semplificata,
 * gli NPC e gli oggetti presenti, e il personaggio come un cerchio che
 * si muove con le frecce/WASD. La posizione sullo schermo è puramente
 * visiva: il modello di gioco (GameState, Room) continua a ragionare
 * solo per stanze, non per coordinate. Gli NPC e gli oggetti sono
 * disegnati solo a scopo illustrativo: l'interazione vera avviene
 * tramite i pulsanti in GamePanel, non cliccando sul disegno.
 * Le dimensioni dei token sono calcolate in proporzione alla dimensione
 * del pannello, così restano equilibrate qualunque sia la dimensione
 * scelta per la finestra.
 */
public class GameCanvas extends JPanel {

    private static final int STEP = 10;
    private static final int EXIT_ZONE = 40;

    private final GameController controller;
    private double playerX;
    private double playerY;
    private boolean initialized = false;

    public GameCanvas(GameController controller) {
        this.controller = controller;
        setBackground(UiTheme.SURFACE);
        setFocusable(true);

        controller.addListener(event -> repaint());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });
    }

    private int playerSize() {
        return Math.max(18, Math.min(getWidth(), getHeight()) / 22);
    }

    private int tokenSize() {
        return Math.max(22, Math.min(getWidth(), getHeight()) / 18);
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
        drawNpcs(g2);
        drawItems(g2);
        drawPlayer(g2);
    }

    private void drawRoomLabel(Graphics2D g2) {
        Room room = controller.getState().getPosition().getCurrentRoom();
        g2.setColor(UiTheme.TEXT_PRIMARY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
        g2.drawString(room.getName(), 14, 26);
    }

    private void drawExits(Graphics2D g2) {
        Room room = controller.getState().getPosition().getCurrentRoom();
        drawExitIfPresent(g2, room, "nord", getWidth() / 2 - 15, 18);
        drawExitIfPresent(g2, room, "sud", getWidth() / 2 - 15, getHeight() - 14);
        drawExitIfPresent(g2, room, "est", getWidth() - 34, getHeight() / 2);
        drawExitIfPresent(g2, room, "ovest", 14, getHeight() / 2);
    }

    private void drawExitIfPresent(Graphics2D g2, Room room, String direction, int x, int y) {
        room.getExit(direction).ifPresent(exit -> {
            g2.setColor(exit.isPassable() ? UiTheme.EXIT_OPEN : UiTheme.EXIT_LOCKED);
            g2.fillRect(x, y, 30, 8);
        });
    }

    private void drawNpcs(Graphics2D g2) {
        int spacing = tokenSize() + 70;
        int x = 50;
        int y = 55;
        for (Npc npc : controller.getNpcsInCurrentRoom()) {
            if (x > getWidth() - 50) {
                x = 50;
                y += spacing / 2;
            }
            drawToken(g2, x, y, UiTheme.NPC_TOKEN, npc.getName());
            x += spacing;
        }
    }

    private void drawItems(Graphics2D g2) {
        int spacing = tokenSize() + 70;
        int x = 50;
        int y = 55 + spacing / 2 + 20;
        for (Item item : controller.getItemsInCurrentRoom()) {
            if (x > getWidth() - 50) {
                x = 50;
                y += spacing / 2;
            }
            drawToken(g2, x, y, UiTheme.ITEM_TOKEN, item.getName());
            x += spacing;
        }
    }

    private void drawToken(Graphics2D g2, int x, int y, Color color, String label) {
        int size = tokenSize();
        g2.setColor(color);
        g2.fillOval(x - size / 2, y - size / 2, size, size);
        g2.setColor(UiTheme.TEXT_PRIMARY);
        g2.setFont(g2.getFont().deriveFont(12f));
        g2.drawString(label, x - size / 2, y + size / 2 + 14);
    }

    private void drawPlayer(Graphics2D g2) {
        int size = playerSize();
        g2.setColor(UiTheme.PLAYER_TOKEN);
        g2.fillOval((int) playerX - size / 2, (int) playerY - size / 2, size, size);
    }
}