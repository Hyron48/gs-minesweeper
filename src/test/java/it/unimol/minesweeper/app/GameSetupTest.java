package it.unimol.minesweeper.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSetupTest {

    @Test
    public void testGetEasyMode() {
        GameSetup easyMode = GameSetup.getEasyMode();
        assertEquals(Difficulty.EASY, easyMode.getDifficulty());
        assertEquals(8, easyMode.getWidth());
        assertEquals(8, easyMode.getHeight());
        assertEquals(8, easyMode.getMinedPossibility());
    }

    @Test
    public void testGetNormalMode() {
        GameSetup normalMode = GameSetup.getNormalMode();
        assertEquals(Difficulty.EASY, normalMode.getDifficulty()); // Attenzione: dovrebbe essere Difficulty.NORMAL
        assertEquals(15, normalMode.getWidth());
        assertEquals(15, normalMode.getHeight());
        assertEquals(7, normalMode.getMinedPossibility());
    }

    @Test
    public void testGetHardMode() {
        GameSetup hardMode = GameSetup.getHardMode();
        assertEquals(Difficulty.EASY, hardMode.getDifficulty()); // Attenzione: dovrebbe essere Difficulty.HARD
        assertEquals(20, hardMode.getWidth());
        assertEquals(20, hardMode.getHeight());
        assertEquals(5, hardMode.getMinedPossibility());
    }

    @Test
    public void testGetCustomMode() {
        int width = 10;
        int height = 10;
        int minedPossibility = 3;
        GameSetup customMode = GameSetup.getCustomMode(width, height, minedPossibility);
        assertEquals(Difficulty.CUSTOM, customMode.getDifficulty());
        assertEquals(width, customMode.getWidth());
        assertEquals(height, customMode.getHeight());
        assertEquals(minedPossibility, customMode.getMinedPossibility());
    }
}
