package it.unimol.minesweeper.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testConstructor() {
        Board board = new Board(10, 10, 5);
        assertNotNull(board);
        assertFalse(board.isDestroyed());
        assertFalse(board.isGivenUp());
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
    public void testExploreBox() {
        // Crea un input fittizio per simulare l'input dell'utente
        String input = "e\n1\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Crea uno scanner per leggere l'input
        Scanner scanner = new Scanner(in);

        // Create a 3x3 board with one mined field
        Board board = new Board(3, 3, 2);
        board.getFields()[1][1] = new Box(true);

        // Ensure the mined field is explored and destroys the board
        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        // Reset System.in
        System.setIn(System.in);
    }

    @Test
    public void testMarkUnmark() {
        // Create a 3x3 board with one mined field
        Board board = new Board(3, 3, 9);
        board.getFields()[1][1] = new Box(true);

        // Mark a box and ensure it's marked
        assertFalse(board.getFields()[0][0].isMarked());
        board.mark(0, 0);
        assertTrue(board.getFields()[0][0].isMarked());

        // Unmark the box and ensure it's unmarked
        board.unmark(0, 0);
        assertFalse(board.getFields()[0][0].isMarked());
    }

    @Test
    public void testGetUserInput() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("e\n1\n1\nx\n".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Create a 3x3 board with one mined field
        Board board = new Board(3, 3, 9);
        board.getFields()[1][1] = new Box(true);

        // Ensure exploreBox is called when 'e' is input
        assertFalse(board.isDestroyed());
        board.getUserInput(scanner);
        assertTrue(board.isDestroyed());

        // Reset System.in
        System.setIn(System.in);
    }
}