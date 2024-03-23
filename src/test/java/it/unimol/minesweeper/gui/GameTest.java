package it.unimol.minesweeper.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        game = new Game();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testChooseDifficultyAndPlay() {
        InputStream in = new ByteArrayInputStream("5\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(in);

        assertDoesNotThrow(() -> game.chooseDifficultyAndPlay(scanner));
    }

    @Test
    public void testShowHighScore() {
        assertDoesNotThrow(() -> game.showHighScore());
    }

    @Test
    public void testMainLoop() {
        // New Game
        Scanner scanner = new Scanner(new ByteArrayInputStream("1\n5\n3\n".getBytes()));
        game.mainLoop(scanner);

        assertTrue(outputStream.toString().contains("Select map size:"));

        // Show HighScore
        scanner = new Scanner(new ByteArrayInputStream("2\n3\n".getBytes()));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);
        game.mainLoop(scanner);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Easy Mode"));
        assertTrue(actualOutput.contains("Normal Mode"));
        assertTrue(actualOutput.contains("Hard Mode"));
        System.setOut(originalOut);

        // Exit
        scanner = new Scanner(new ByteArrayInputStream("3\n".getBytes()));

        Scanner finalScanner = scanner;
        assertDoesNotThrow(() -> game.mainLoop(finalScanner));
    }

    @Test
    public void testCreateNewLists() {
        game.setManuallyHighScore();

        assertNotNull(game.getEasyHighScoreList().toString());
        assertNotNull(game.getNormalHighScoreList().toString());
        assertNotNull(game.getHardHighScoreList().toString());
    }

    @Test
    public void testMainLoopInvalidInput() {
        String input = "invalid\n3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Scanner scanner = new Scanner(in);

        assertDoesNotThrow(() -> game.mainLoop(scanner));
    }
}
