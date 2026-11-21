package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;

/**
 * Menu principale, mostrato dopo la schermata del titolo: nuova
 * partita, carica partita (disponibile solo se esiste un salvataggio),
 * guida ed esci.
 */
public class MenuPanel extends JPanel {

    public MenuPanel(Runnable onNewGame, Runnable onLoadGame, boolean loadAvailable,
                     Runnable onGuide, Runnable onExit) {
        setLayout(new GridBagLayout());
        setBackground(UiTheme.BACKGROUND);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createEnabledButton("Nuova partita", onNewGame));
        content.add(Box.createVerticalStrut(14));
        content.add(loadAvailable
                ? createEnabledButton("Carica partita", onLoadGame)
                : createDisabledButton("Carica partita", "Nessuna partita salvata"));
        content.add(Box.createVerticalStrut(14));
        content.add(createEnabledButton("Guida", onGuide));
        content.add(Box.createVerticalStrut(14));
        content.add(createEnabledButton("Esci", onExit));

        add(content);
    }

    private JButton createEnabledButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> action.run());
        UiTheme.styleMenuButton(button);
        return button;
    }

    private JButton createDisabledButton(String label, String tooltip) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        button.setToolTipText(tooltip);
        UiTheme.styleMenuButton(button);
        return button;
    }
}