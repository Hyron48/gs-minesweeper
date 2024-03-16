package it.unimol.minesweeper.app;

public class GameSetup {

    private int width;
    private int height;
    private Difficulty difficulty;
    private int minedPossibility;

    // Constructor
    public GameSetup(Difficulty difficulty, int width, int height, int minedPossibility) {
        this.difficulty = difficulty;
        this.width = width;
        this.height = height;
        this.minedPossibility = minedPossibility;
    }

    // Default Builder

    public static GameSetup getEasyMode() {
        return new GameSetup(Difficulty.EASY, 8, 8, 90);
    }

    public static GameSetup getNormalMode() {
        return new GameSetup(Difficulty.EASY, 15, 15, 7);
    }

    public static GameSetup getHardMode() {
        return new GameSetup(Difficulty.EASY, 20, 20, 5);
    }

    public static GameSetup getCustomMode(int width, int height, int minedPossibility) {
        return new GameSetup(Difficulty.CUSTOM, width, height, minedPossibility);
    }

    // Getter

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMinedPossibility() {
        return minedPossibility;
    }
}
