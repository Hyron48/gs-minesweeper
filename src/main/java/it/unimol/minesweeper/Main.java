package it.unimol.minesweeper;

import it.unimol.minesweeper.gui.Game;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        game.mainLoop(scanner);
    }
}
