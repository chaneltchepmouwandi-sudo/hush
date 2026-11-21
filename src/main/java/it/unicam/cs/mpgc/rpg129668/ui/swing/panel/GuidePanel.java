package it.unicam.cs.mpgc.rpg129668.ui.swing.panel;

import it.unicam.cs.mpgc.rpg129668.ui.swing.UiTheme;

import javax.swing.*;
import java.awt.*;

/**
 * Schermata della guida: spiega l'obiettivo del gioco, i comandi e
 * qualche consiglio, con un pulsante per tornare al menu.
 */
public class GuidePanel extends JPanel {

    private static final String GUIDE_TEXT = """
            OBIETTIVO
            Sei una bambina rinchiusa in un ospedale psichiatrico insieme a tuo fratello. Una notte lui è sparito e nessuno ti dice che cosa è successo. Scoprilo da sola: parla con gli altri pazienti, leggi quello che trovi e conquista la fiducia di chi sa la verità.

            COMANDI
            • Frecce o WASD: muovono il personaggio nella stanza.
            • Raggiungi un bordo dove c'è un'uscita per passare alla stanza successiva. Un segno verde indica un passaggio libero, uno rosso un passaggio chiuso.
            • I pulsanti in alto permettono di parlare con chi è nella stanza, raccogliere oggetti, aprire i passaggi chiusi, usare le pillole e leggere i documenti che hai con te.
            • "Salva partita" (in basso) salva i progressi; "Carica partita" (dal menu) li riprende.

            PANNELLO DI STATO
            A destra vedi le tue statistiche, la fiducia che hai guadagnato e gli oggetti che porti con te. Un numero tra parentesi accanto a una statistica indica un effetto temporaneo.

            CONSIGLI
            • Le chiavi aprono i passaggi chiusi: se ne hai una, prova "Apri verso...".
            • Qualcuno ti aiuterà solo in cambio di qualcosa: ascolta che cosa ti chiede.
            • Le pillole cambiano le tue statistiche per qualche turno. Alcune ti aiutano, altre no: pensa a quando usarle.
            • Ogni azione fa passare un turno; muoversi dentro la stanza no.
            """;

    public GuidePanel(Runnable onBack) {
        setLayout(new BorderLayout(0, 16));
        setBackground(UiTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel title = new JLabel("GUIDA", SwingConstants.CENTER);
        title.setForeground(UiTheme.ACCENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 30f));
        add(title, BorderLayout.NORTH);

        JTextArea text = new JTextArea(GUIDE_TEXT);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBackground(UiTheme.SURFACE);
        text.setForeground(UiTheme.TEXT_PRIMARY);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        text.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        text.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(BorderFactory.createLineBorder(UiTheme.BUTTON_BORDER));
        add(scroll, BorderLayout.CENTER);

        JButton back = new JButton("Indietro");
        back.addActionListener(e -> onBack.run());
        UiTheme.styleMenuButton(back);
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.add(back);
        add(footer, BorderLayout.SOUTH);
    }
}