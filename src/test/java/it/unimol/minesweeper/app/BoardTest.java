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
                if (!board.getFieldsForTesting()[i][j].isMined()) {
                    board.exploreBox(i, j);
                }
            }
        }

        assertTrue(board.isMapCompleted());

        board = new Board(5, 5, 5);
        board.getFieldsForTesting()[1][1].explore();

        // Simulate exploring all non-explored fields
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getFieldsForTesting()[i][j].isExplored()) {
                    board.exploreBox(i, j);
                }
            }
        }
        assertTrue(!board.isMapCompleted());

        board = new Board(5, 5, 5);

        // Simulate exploring all non-mined fields
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!board.getFieldsForTesting()[i][j].isExplored() && !board.getFieldsForTesting()[i][j].isExplored()) {
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
        board.getFieldsForTesting()[1][1] = new Box(true);

        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        // Mark
        scanner = new Scanner(new ByteArrayInputStream("m\n1\n1\n".getBytes()));

        board = new Board(5, 5, 5);
        board.getFieldsForTesting()[1][1] = new Box(true);

        assertEquals(board.getFieldsForTesting()[1][1].getAppearance(), "-");
        board.getUserInput(scanner);
        assertEquals(board.getFieldsForTesting()[1][1].getAppearance(), "#");

        // Unmark
        scanner = new Scanner(new ByteArrayInputStream("u\n1\n1\n".getBytes()));
        assertEquals(board.getFieldsForTesting()[1][1].getAppearance(), "#");
        board.getUserInput(scanner);
        assertEquals(board.getFieldsForTesting()[1][1].getAppearance(), "-");

        // Give Up
        scanner = new Scanner(new ByteArrayInputStream("x\n".getBytes()));
        assertFalse(board.isGivenUp());
        board.getUserInput(scanner);
        assertTrue(board.isGivenUp());

        // Default
        scanner = new Scanner(new ByteArrayInputStream("f\n".getBytes()));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        board.getUserInput(scanner);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Invalid input. Enter x for exit."));
        System.setOut(originalOut);
    }

    @Test
    public void testExploreWithoutNearestMined() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("e\n1\n1\n".getBytes()));

        Board board = new Board(5, 5, 5);
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board.getFieldsForTesting()[i][j] = new Box(false);
            }
        }

        board.getUserInput(scanner);

        assertEquals(" ", board.getFieldsForTesting()[1][1].getAppearance());
    }

    @Test
    public void testMarkUnmark() {
        Board board = new Board(3, 3, 9);
        board.getFieldsForTesting()[1][1] = new Box(true);

        assertFalse(board.getFieldsForTesting()[0][0].isMarked());
        board.mark(0, 0);
        assertTrue(board.getFieldsForTesting()[0][0].isMarked());

        board.unmark(0, 0);
        assertFalse(board.getFieldsForTesting()[0][0].isMarked());
    }

    @Test
    public void testGetUserInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("e\n1\n1\nx\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        Board board = new Board(5, 5, 9);
        board.getFieldsForTesting()[1][1] = new Box(true);

        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        System.setIn(System.in);
    }

    @Test
    public void testCatchSaveInt() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("e\n1\ncd\n1\n".getBytes()));
        Board board = new Board(5, 5, 5);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        board.getUserInput(scanner);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("Not a valid number."));
        System.setOut(originalOut);
    }

    @Test
    public void testCatchSaveIntWithRange() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("e\n1\n123\n1\n".getBytes()));
        Board board = new Board(5, 5, 5);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        board.getUserInput(scanner);

        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("The input has to be between 0 and 4."));
        System.setOut(originalOut);

        scanner = new Scanner(new ByteArrayInputStream("e\n1\n-231\n1\n".getBytes()));
        board = new Board(5, 5, 5);

        outputStream = new ByteArrayOutputStream();
        newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        board.getUserInput(scanner);

        actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains("The input has to be between 0 and 4."));
        System.setOut(originalOut);
    }

    @Test
    public void testDisplay() {
        Board board = new Board(5, 5, 5);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(outputStream);

        System.setOut(newOut);

        board.display();

        String actualOutput = outputStream.toString();
        String expectedOutput = "\n" +
                "\n" +
                "\n" +
                "     0     1     2     3     4     \n" +
                "\n" +
                "0    -     -     -     -     -     \n" +
                "\n" +
                "1    -     -     -     -     -     \n" +
                "\n" +
                "2    -     -     -     -     -     \n" +
                "\n" +
                "3    -     -     -     -     -     \n" +
                "\n" +
                "4    -     -     -     -     -     \n" +
                "\n";
        assertEquals(actualOutput, expectedOutput);
        System.setOut(originalOut);
    }
}