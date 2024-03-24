package it.unimol.minesweeper.gui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unimol.minesweeper.app.Board;
import it.unimol.minesweeper.app.Difficulty;
import it.unimol.minesweeper.app.GameSetup;
import it.unimol.minesweeper.app.utils.HighScoreList;
import it.unimol.minesweeper.app.utils.StopWatch;
import it.unimol.minesweeper.app.utils.Tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final String SAVE_PATH = "Minesweeper.data";
    private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    private HighScoreList easyHighScoreList;
    private HighScoreList normalHighScoreList;
    private HighScoreList hardHighScoreList;

    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "This method is intended for testing purposes only")
    public HighScoreList getEasyHighScoreList() {
        return easyHighScoreList;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "This method is intended for testing purposes only")
    public HighScoreList getNormalHighScoreList() {
        return normalHighScoreList;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "This method is intended for testing purposes only")
    public HighScoreList getHardHighScoreList() {
        return hardHighScoreList;
    }

    public Game() {
        try {
            File save = new File(SAVE_PATH);
            if (save.exists()) {
                load();
            } else {
                this.setManuallyHighScore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setManuallyHighScore() {
        easyHighScoreList = new HighScoreList("Easy Mode", 5, "seconds", true);
        normalHighScoreList = new HighScoreList("Normal Mode", 5, "seconds", true);
        hardHighScoreList = new HighScoreList("Hard Mode", 5, "seconds", true);
    }

    public void mainLoop(Scanner scanner) {
        while (true) {
            System.out.println("[1] New game");
            System.out.println("[2] High score");
            System.out.println("[3] Exit");

            System.out.println("Input: ");
            String input = scanner.nextLine();
            System.out.println();

            if (input.equals("1")) {
                this.chooseDifficultyAndPlay(scanner);
            } else if (input.equals("2")) {
                this.showHighScore();
            } else if (input.equals("3")) {
                return;
            } else {
                System.out.println("Your input is invalid.\n");
            }
        }
    }

    public void chooseDifficultyAndPlay(Scanner scanner) {
        System.out.println("Select map size:");
        System.out.println("[1] Small map");
        System.out.println("[2] Normal map");
        System.out.println("[3] Large map");
        System.out.println("[4] Custom map");
        System.out.println("[5] Back to main menu");
        System.out.print("Input: ");
        String input = scanner.nextLine();
        System.out.println();

        this.manageDifficultyChoise(input, scanner);
    }

    //Private Methods
    @SuppressFBWarnings(value = "OBJECT_DESERIALIZATION",
            justification = "Can't change ObjectInputStream and I need to deserialize the object")
    private void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH))) {
            List<HighScoreList> scoreLists = (List<HighScoreList>) ois.readObject();
            easyHighScoreList = scoreLists.get(0);
            normalHighScoreList = scoreLists.get(1);
            hardHighScoreList = scoreLists.get(2);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void manageDifficultyChoise(String input, Scanner scanner) {
        if (input.equals("1")) {
            this.play(GameSetup.getEasyMode(), scanner);
        } else if (input.equals("2")) {
            this.play(GameSetup.getNormalMode(), scanner);
        } else if (input.equals("3")) {
            this.play(GameSetup.getHardMode(), scanner);
        } else if (input.equals("4")) {
            int width = Tools.saveIntInput("Width: ", Board.min, Board.max);
            int height = Tools.saveIntInput("Height: ", Board.min, Board.max);
            int minedPossibility = Tools.saveIntInput("Mined Possibility: ", Board.minimumMined, Integer.MAX_VALUE);
            this.play(GameSetup.getCustomMode(height, width, minedPossibility), scanner);
        } else if (input.equals("5")) {
            System.out.printf("ok");
            return;
        }
    }

    public void showHighScore() {
        System.out.println(easyHighScoreList);
        System.out.println(normalHighScoreList);
        System.out.println(hardHighScoreList);
    }

    private void play(GameSetup mode, Scanner scanner) {
        Board board = new Board(mode.getWidth(), mode.getHeight(), mode.getMinedPossibility());

        StopWatch watch = new StopWatch();
        watch.start();

        board.displayAxisInfo();
        while (!board.isMapCompleted() && !board.isDestroyed() && !board.isGivenUp()) {
            board.display();
            boolean closeLoop = false;
            while (!closeLoop) {
                closeLoop = board.getUserInput(scanner);
            }
        }

        watch.stop();

        if (board.isGivenUp()) {
            System.out.println("You gave up.");
        } else {
            board.display();
            if (board.isMapCompleted()) {
                System.out.println("You've won the game!");
                this.checkForHighScore(mode.getDifficulty(), watch);
            } else {
                System.out.println("You stepped on a mine.");
            }
        }
        System.out.println();
    }

    private void checkForHighScore(Difficulty difficulty, StopWatch watch) {
        HighScoreList rightList;
        if (difficulty.equals(Difficulty.EASY)) {
            rightList = easyHighScoreList;
        } else if (difficulty.equals(Difficulty.NORMAL)) {
            rightList = normalHighScoreList;
        } else if (difficulty.equals(Difficulty.HARD)) {
            rightList = hardHighScoreList;
        } else {
            return;
        }

        if (rightList.checkIfWorthy(watch.getSeconds())) {
            System.out.println("Congratulations! You reached a new high score!");
            String name = Tools.saveStringInput("Enter your name: ", 3, 20);
            rightList.add(name, watch.getSeconds());
            this.save();
        }
    }

    private void save() {
        List<HighScoreList> scoreLists = new ArrayList<HighScoreList>();
        scoreLists.add(easyHighScoreList);
        scoreLists.add(normalHighScoreList);
        scoreLists.add(hardHighScoreList);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
            oos.writeObject(scoreLists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
