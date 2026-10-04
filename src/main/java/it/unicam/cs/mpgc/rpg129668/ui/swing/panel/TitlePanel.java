package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Schermata iniziale: titolo del gioco e invito a procedere.
 * Non contiene logica di gioco: notifica solo che il giocatore
 * vuole continuare, tramite la callback fornita.
 */
public class TitlePanel extends JPanel {

    public TitlePanel(Runnable onContinue) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setFocusable(true);

        JLabel title = new JLabel("HUSH");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 64f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("Premi un tasto per continuare");
        hint.setForeground(Color.LIGHT_GRAY);
        hint.setFont(hint.getFont().deriveFont(Font.PLAIN, 14f));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(title);
        content.add(Box.createVerticalStrut(20));
        content.add(hint);

        add(content);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                onContinue.run();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onContinue.run();
            }
        });
    }
}