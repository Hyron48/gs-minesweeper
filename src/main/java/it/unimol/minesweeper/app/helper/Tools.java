package it.unimol.minesweeper.app.helper;

import java.util.Scanner;

public class Tools {
    private static Scanner scanner = new Scanner(System.in);

    public static String saveStringInput(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.length() < minLength || input.length() > maxLength) {
                System.out.printf("Your input must be %d to %d characters long.\n", minLength, maxLength);
            } else {
                return input;
            }
        }
    }

    public static int saveIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.valueOf(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Not a valid number.");
            }
        }
    }

    public static int saveIntInput(String prompt, int lowerRange, int upperRange) {
        while (true) {
            int input = saveIntInput(prompt);
            if (input > upperRange || input < lowerRange) {
                System.out.println("The input has to be between " + lowerRange + " and " + upperRange + ".");
            } else {
                return input;
            }
        }
    }

    public static boolean equalsOneOf(boolean ignoreCase, String string, String... symbols) {
        for (String symbol : symbols) {
            if (ignoreCase) {
                if (string.equalsIgnoreCase(symbol)) return true;
            } else {
                if (string.equals(symbol)) return false;
            }
        }
        return false;
    }
}
