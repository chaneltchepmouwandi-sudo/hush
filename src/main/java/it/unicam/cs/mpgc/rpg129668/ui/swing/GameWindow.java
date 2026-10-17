package it.unicam.cs.mpgc.rpg129668.ui.swing;

import it.unicam.cs.mpgc.rpg129668.controller.GameController;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.GamePanel;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.MenuPanel;
import it.unicam.cs.mpgc.rpg129668.ui.swing.panel.TitlePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Finestra principale: schermata iniziale, menu, e pannello di gioco,
 * mostrati uno alla volta tramite CardLayout. La partita (GameController)
 * viene costruita solo quando il giocatore sceglie "Nuova partita" o
 * "Carica partita", non prima.
 */
public class GameWindow extends JFrame {

    private static final String TITLE_CARD = "title";
    private static final String MENU_CARD = "menu";
    private static final String GAME_CARD = "game";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final TitlePanel titlePanel;
    private final Supplier<GameController> newGameFactory;
    private final Supplier<GameController> loadGameFactory;
    private final Consumer<GameController> onSaveRequested;
    private GamePanel gamePanel;

    public GameWindow(Supplier<GameController> newGameFactory, Supplier<GameController> loadGameFactory,
                      boolean saveAvailable, Consumer<GameController> onSaveRequested) {
        super("Hush");

        this.newGameFactory = newGameFactory;
        this.loadGameFactory = loadGameFactory;
        this.onSaveRequested = onSaveRequested;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);

        titlePanel = new TitlePanel(this::showMenu);
        MenuPanel menuPanel = new MenuPanel(this::startNewGame, this::continueGame, saveAvailable, () -> System.exit(0));

        cards.add(titlePanel, TITLE_CARD);
        cards.add(menuPanel, MENU_CARD);
        add(cards);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (gamePanel != null) {
                    gamePanel.focusCanvas();
                }
            }
        });

        cardLayout.show(cards, TITLE_CARD);
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

    private void startNewGame() {
        openGame(newGameFactory.get());
    }

    private void continueGame() {
        openGame(loadGameFactory.get());
    }

    private void openGame(GameController controller) {
        if (gamePanel != null) {
            cards.remove(gamePanel);
        }
        gamePanel = new GamePanel(controller, () -> onSaveRequested.accept(controller));
        cards.add(gamePanel, GAME_CARD);
        cardLayout.show(cards, GAME_CARD);
        gamePanel.focusCanvas();
    }
}