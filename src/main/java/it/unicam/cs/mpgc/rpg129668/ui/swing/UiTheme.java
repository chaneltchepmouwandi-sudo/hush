package it.unicam.cs.mpgc.rpg129668.ui.swing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

/**
 * Colori condivisi da tutti i pannelli dell'interfaccia (titolo, menu,
 * pannello di gioco, vista della stanza), per mantenere un aspetto
 * coerente tra le schermate invece di affidarsi ai colori di default
 * di Swing. La palette usa toni freddi e ardesia per lo sfondo (il
 * corridoio d'ospedale di notte), con un accento caldo per ciò che è
 * "vivo" (titolo, NPC, oggetti), a contrasto con lo sfondo.
 */
public final class UiTheme {

    public static final Color BACKGROUND = new Color(22, 27, 34);
    public static final Color SURFACE = new Color(30, 36, 44);
    public static final Color TEXT_PRIMARY = new Color(230, 227, 220);
    public static final Color TEXT_SECONDARY = new Color(140, 150, 165);

    public static final Color BUTTON_BACKGROUND = new Color(38, 45, 54);
    public static final Color BUTTON_BORDER = new Color(70, 80, 92);

    public static final Color ACCENT = new Color(212, 163, 92);
    public static final Color NPC_TOKEN = new Color(212, 140, 90);
    public static final Color ITEM_TOKEN = new Color(201, 178, 110);
    public static final Color PLAYER_TOKEN = new Color(120, 155, 195);

    public static final Color EXIT_OPEN = new Color(100, 165, 130);
    public static final Color EXIT_LOCKED = new Color(185, 100, 95);

    private UiTheme() {
        // Solo costanti statiche, nessuna istanza necessaria.
    }

    /**
     * Applica lo stile condiviso a un pulsante.
     */
    public static void styleButton(JButton button) {
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER));
    }

    /**
     * Applica lo stile condiviso a un pulsante del menu principale,
     * più grande dei pulsanti di azione in gioco.
     */
    public static void styleMenuButton(JButton button) {
        styleButton(button);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 20f));
        button.setPreferredSize(new Dimension(260, 48));
    }
}