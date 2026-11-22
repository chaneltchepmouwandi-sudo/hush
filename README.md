# Hush

Gioco di ruolo in Java (Swing) realizzato per il corso di Metodologie di Programmazione, A.A. 2025/26.

Una bambina è rinchiusa in un ospedale psichiatrico insieme al fratello. Una notte lui scompare e nessuno vuole risponderle: lei deve scoprire da sola cosa è successo, aiutando gli altri pazienti in cambio di indizi, usando chiavi e pillole e guadagnandosi la fiducia del personale.

**Autore:** Chanel Dylane Tchepmou Wandji, matricola 129668

## Requisiti

- JDK 17 o superiore (sviluppato e testato con JDK 25)
- Connessione a internet al primo avvio per il download automatico delle dipendenze tramite Gradle
- Nessuna installazione di Gradle richiesta: è sufficiente il wrapper incluso nel repository

## Compilazione ed esecuzione

```bash
./gradlew build
./gradlew run
```

Su Windows: `gradlew.bat build` e `gradlew.bat run` (in PowerShell: `.\gradlew build` e `.\gradlew run`).

Il comando `build` compila il progetto ed esegue anche la suite di test JUnit.

## Come si gioca

- **Frecce o WASD** muovono il personaggio nella stanza; raggiungendo un bordo dove c'è un'uscita si passa alla stanza successiva.
- I **pulsanti** in alto permettono di parlare con i personaggi presenti, raccogliere oggetti, aprire i passaggi chiusi a chiave, usare le pillole e leggere i documenti.
- Il pannello a destra mostra statistiche, fiducia degli NPC e inventario.
- **Salva partita** (in gioco) scrive il salvataggio nel file `hush-save.json`, nella cartella da cui si avvia il gioco. **Carica partita** (nel menu) lo riprende.
- La **Guida** nel menu riassume obiettivo e comandi.

## Documentazione

La documentazione completa del progetto è disponibile nella [Wiki](https://github.com/chaneltchepmouwandi-sudo/hush/wiki), articolata nelle seguenti sezioni:

- [Funzionalità implementate](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Funzionalita-implementate)
- [Architettura e responsabilità di classi e interfacce](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Architettura-e-responsabilita)
- [Organizzazione dei dati e persistenza](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Dati-e-persistenza)
- [Meccanismi di estendibilità](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Meccanismi-di-estendibilita)
- [Uso di strumenti di AI](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Uso-di-strumenti-di-AI)

## Uso di strumenti di AI

Per lo sviluppo di questo progetto sono stati utilizzati **Claude** (Anthropic) e **Gemini** (Google) tramite interfaccia conversazionale, impiegati in modo complementare come consulenti tecnici — confrontando le diverse soluzioni e analisi proposte da entrambi i modelli — e come supporto nella stesura dei test:

- **Concept e requisiti:** ideazione del gioco, delle meccaniche e stesura dei contenuti a cura dello studente, con spunti degli strumenti AI per varianti narrative e dialoghi degli NPC.
- **Architettura e design:** confronto tra alternative strutturali, pattern e punti di estensione; definizione dei requisiti, separazione *Model-View-Controller*, persistenza tramite JSON, rispetto dei principi SOLID e assegnazione delle responsabilità delle classi a cura dello studente.
- **Sviluppo e interfaccia grafica (Swing):** consulenza puntuale su sintassi, API standard Java/Swing e parsing JSON; progettazione e scrittura del codice sorgente nel package `it.unicam.cs.mpgc.rpg129668` e dell'intera interfaccia grafica svolte dallo studente.
- **Testing e debug:** supporto nella scrittura dei casi di test JUnit e nell'analisi di specifici errori; revisione, adattamento, esecuzione dei test e playtesting manuale effettuati dallo studente.

Il dettaglio completo delle attività e delle relative finalità è riportato nella pagina [Uso di strumenti di AI](https://github.com/chaneltchepmouwandi-sudo/hush/wiki/Uso-di-strumenti-di-AI) della Wiki.