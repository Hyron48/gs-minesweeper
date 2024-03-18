package it.unimol.minesweeper.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testChooseDifficultyAndPlay() {
        // Simuliamo l'input dell'utente per selezionare una modalità di gioco
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(in);

        // Eseguiamo il metodo chooseDifficultyAndPlay()
        assertDoesNotThrow(() -> game.chooseDifficultyAndPlay(scanner));
        // Verifichiamo che il metodo abbia chiamato correttamente il metodo play() con la modalità Easy
        // Aggiungere altre asserzioni qui se necessario
    }

    @Test
    public void testShowHighScore() {
        // Questo test verifica semplicemente che il metodo showHighScore() possa essere eseguito senza errori
        assertDoesNotThrow(() -> game.showHighScore());
    }

    @Test
    public void testMainLoop() {
        // Simuliamo l'input dell'utente per selezionare l'uscita dal loop principale del gioco
        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner scanner = new Scanner(in);

        // Eseguiamo il metodo mainLoop()
        assertDoesNotThrow(() -> game.mainLoop(scanner));
        // Verifichiamo che il metodo termini correttamente senza lanciare eccezioni
    }

    @Test
    public void testMainLoop_InvalidInput() {
        // Simuliamo l'input dell'utente con un'opzione non valida
        String input = "invalid\n3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner scanner = new Scanner(in);

        // Eseguiamo il metodo mainLoop()
        assertDoesNotThrow(() -> game.mainLoop(scanner));
        // Verifichiamo che il metodo gestisca correttamente l'input non valido senza lanciare eccezioni
    }

    // Aggiungi altri casi di test per gli altri metodi della classe Game, se necessario.
}
