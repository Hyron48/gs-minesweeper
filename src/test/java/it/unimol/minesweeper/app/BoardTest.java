package it.unimol.minesweeper.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testConstructor() {
        // Valid width and height
        Board board = new Board(10, 10, 5);
        assertNotNull(board);
        assertFalse(board.isDestroyed());
        assertFalse(board.isGivenUp());

        board = new Board(3, 3, 2);
        assertNotNull(board);
        assertFalse(board.isDestroyed());
        assertFalse(board.isGivenUp());
        assertEquals(board.getWidth(), 5);
        assertEquals(board.getHeight(), 5);

        board = new Board(101, 101, 1);
        assertNotNull(board);
        assertFalse(board.isDestroyed());
        assertFalse(board.isGivenUp());
        assertEquals(board.getWidth(), 99);
        assertEquals(board.getHeight(), 99);
    }

    @Test
    public void testDisplayAxisInfo() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        new Board(5, 5, 2).displayAxisInfo();

        String actualOutput = outputStream.toString();
        String expectedOutput = "X: Line number\n\nY: Column number\n\n";
        assertEquals(expectedOutput, actualOutput);
        System.setOut(originalOut);
    }

    @Test
    public void testIsMapCompleted() {
        Board board = new Board(5, 5, 2);
        assertFalse(board.isMapCompleted());

        // Simulate exploring all non-mined fields
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!board.getFields()[i][j].isMined()) {
                    board.exploreBox(i, j);
                }
            }
        }

        assertTrue(board.isMapCompleted());
    }

    @Test
    public void getUserInput() {
        // Explore
        Scanner scanner = new Scanner(new ByteArrayInputStream("e\n1\n1\n".getBytes()));

        Board board = new Board(5, 5, 5);
        board.getFields()[1][1] = new Box(true);

        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        // Mark
        scanner = new Scanner(new ByteArrayInputStream("m\n1\n1\n".getBytes()));

        board = new Board(5, 5, 5);
        board.getFields()[1][1] = new Box(true);

        assertEquals(board.getFields()[1][1].getAppearance(), "-");
        board.getUserInput(scanner);
        assertEquals(board.getFields()[1][1].getAppearance(), "#");

        scanner = new Scanner(new ByteArrayInputStream("u\n1\n1\n".getBytes()));
        assertEquals(board.getFields()[1][1].getAppearance(), "#");
        board.getUserInput(scanner);
        assertEquals(board.getFields()[1][1].getAppearance(), "-");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        scanner = new Scanner(new ByteArrayInputStream("z\n".getBytes()));
        board.getUserInput(scanner);

        String actualOutput = outputStream.toString();
        String expectedOutput = "Invalid input. Enter x for exit.\n";
        assertEquals(expectedOutput, actualOutput);
        System.setOut(originalOut);

    }

    @Test
    public void testMarkUnmark() {
        Board board = new Board(3, 3, 9);
        board.getFields()[1][1] = new Box(true);

        assertFalse(board.getFields()[0][0].isMarked());
        board.mark(0, 0);
        assertTrue(board.getFields()[0][0].isMarked());

        board.unmark(0, 0);
        assertFalse(board.getFields()[0][0].isMarked());
    }

    @Test
    public void testGetUserInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("e\n1\n1\nx\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        Board board = new Board(5, 5, 9);
        board.getFields()[1][1] = new Box(true);

        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        System.setIn(System.in);
    }
}