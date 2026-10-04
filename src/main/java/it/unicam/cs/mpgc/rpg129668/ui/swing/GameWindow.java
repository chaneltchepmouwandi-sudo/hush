package it.unicam.cs.mpgc.rpg129668.ui.swing;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.GamePanel;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.MenuPanel;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.TitlePanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra principale: schermata iniziale, menu, e pannello di gioco,
 * mostrati uno alla volta tramite CardLayout.
 */
public class GameWindow extends JFrame {

    private static final String TITLE_CARD = "title";
    private static final String MENU_CARD = "menu";
    private static final String GAME_CARD = "game";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final TitlePanel titlePanel;

    public GameWindow(GameController controller) {
        super("Hush");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        titlePanel = new TitlePanel(this::showMenu);
        MenuPanel menuPanel = new MenuPanel(this::showGame, () -> System.exit(0));
        GamePanel gamePanel = new GamePanel(controller);

        cards.add(titlePanel, TITLE_CARD);
        cards.add(menuPanel, MENU_CARD);
        cards.add(gamePanel, GAME_CARD);
        add(cards);

        cardLayout.show(cards, TITLE_CARD);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.focusCanvas();
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            titlePanel.requestFocusInWindow();
        }
    }

    private void showMenu() {
        cardLayout.show(cards, MENU_CARD);
    }

    private void showGame() {
        cardLayout.show(cards, GAME_CARD);
    }
}