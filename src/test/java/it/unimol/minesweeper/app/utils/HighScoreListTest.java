package it.unimol.minesweeper.app.utils;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoreListTest {

    private HighScoreList highScoreList;

    @Before
    public void setUp() {
        highScoreList = new HighScoreList("Test List", 3, "points");
    }

    @Test
    public void testAddMultipleEntries() {
        assertTrue(highScoreList.add("Player1", 100));
        assertTrue(highScoreList.add("Player2", 200));
        assertTrue(highScoreList.add("Player3", 150));
        assertFalse(highScoreList.add("Player4", 50)); // Exceeds maxEntries
    }

    @Test
    public void testCheckIfWorthyWhenEntriesAreLessThanMaxEntries() {
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        assertTrue(highScoreList.checkIfWorthy(70));

        highScoreList = new HighScoreList("Test", 3, "points", true);
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        highScoreList.add("Player3", 70);
        assertTrue(highScoreList.checkIfWorthy(55));

        highScoreList = new HighScoreList("Test", 3, "points", true);
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        highScoreList.add("Player3", 70);
        assertFalse(highScoreList.checkIfWorthy(75));

        highScoreList = new HighScoreList("Test", 3, "points", false);
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        highScoreList.add("Player3", 70);
        assertFalse(highScoreList.checkIfWorthy(30));
        assertTrue(highScoreList.checkIfWorthy(80));
    }

    @Test
    public void testAdd() {
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        highScoreList.add("Player3", 70);
        assertTrue(highScoreList.add("Player4", 75));

        highScoreList = new HighScoreList("Test", 3, "points", false);
        highScoreList.add("Player1", 50);
        highScoreList.add("Player2", 60);
        highScoreList.add("Player3", 70);
        assertFalse(highScoreList.add("Player4", 35));
    }

    @Test
    public void testEntriesOrder() {
        assertTrue(highScoreList.add("Player1", 100));
        assertTrue(highScoreList.add("Player2", 200));
        assertTrue(highScoreList.add("Player3", 150));
    }

    @Test
    public void testToString() {
        highScoreList.add("Player1", 100);
        highScoreList.add("Player2", 200);
        highScoreList.add("Player3", 150);
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
        String expectedDate = now.format(formatter);

        String expected = "Test List\n\n1: Player2 - 200.0 points - " + expectedDate + "\n" +
                "2: Player3 - 150.0 points - " + expectedDate + "\n" +
                "3: Player1 - 100.0 points - " + expectedDate + "\n";

        assertEquals(expected, highScoreList.toString());
    }
}
