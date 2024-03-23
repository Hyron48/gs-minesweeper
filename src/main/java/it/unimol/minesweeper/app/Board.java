package it.unimol.minesweeper.app;

import java.security.SecureRandom;
import java.util.Scanner;

public class Board {
    public static int min = 5;
    public static int max = 99;
    public static int minimumMined = 2;

    private int width;
    private int height;
    private Box[][] fields;
    private int minedFields;
    private int fieldsAlreadyExplored;
    private boolean destroyed;
    private boolean givenUp;

    public Board(int width, int height, int minedPossibility) {
        if (width < min) {
            width = min;
            System.out.println("The minimum width is " + min + ".");
        }
        if (width > max) {
            width = max;
            System.out.println("The maximum width is " + max + ".");
        }
        if (height < min) {
            height = min;
            System.out.println("The minimum height is " + min + ".");
        }
        if (height > max) {
            height = max;
            System.out.println("The maximum height is " + max + ".");
        }

        if (minedPossibility < minimumMined) {
            minedPossibility = minimumMined;
            System.out.println("The minimum value for mined fields is: 1 in " + minimumMined + ".");
        }

        this.width = width;
        this.height = height;

        this.destroyed = false;
        this.givenUp = false;

        this.setupMap(minedPossibility);
    }

    // Getter

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public boolean isDestroyed() {
        return this.destroyed;
    }

    public boolean isGivenUp() {
        return this.givenUp;
    }

    public boolean isMapCompleted() {
        return width * height - minedFields - fieldsAlreadyExplored == 0;
    }

    public void displayAxisInfo() {
        System.out.println("X: Line number\n");
        System.out.println("Y: Column number\n");
    }

    public Box[][] getFields() {
        return fields;
    }

    // User Action
    public void mark(int x, int y) {
        this.fields[x][y].mark();
    }

    public void unmark(int x, int y) {
        this.fields[x][y].unmark();
    }

    public boolean exploreBox(int x, int y) {
        if (!this.fields[x][y].isExplored() && !this.fields[x][y].isMined()) {
            this.fieldsAlreadyExplored++;
        }

        this.fields[x][y].explore();

        if (this.fields[x][y].isMined()) {
            this.destroyed = true;
            return true;
        }

        int[][] nearbyBox = this.getNearbyBoxes(x, y);

        int numberMinedNearbyBoxes = 0;
        for (int i = 0; i < nearbyBox.length; i++) {
            if (this.fields[nearbyBox[i][0]][nearbyBox[i][1]].isMined()) {
                numberMinedNearbyBoxes++;
            }
        }

        if (numberMinedNearbyBoxes == 0) {
            this.fields[x][y].setAppearance(" ");

            for (int i = 0; i < nearbyBox.length; i++) {
                if (!this.fields[nearbyBox[i][0]][nearbyBox[i][1]].isExplored()) {
                    exploreBox(nearbyBox[i][0], nearbyBox[i][1]);
                }
            }
        } else {
            this.fields[x][y].setAppearance(String.valueOf(numberMinedNearbyBoxes));
            System.out.print("");
        }
        return false;
    }

    //Display Possible action

    public boolean getUserInput(Scanner scanner) {
        System.out.println("Enter one of the following letters to perform an action:");
        System.out.println("m: Mark a box");
        System.out.println("u: Unmark a box");
        System.out.println("e: Explore a box");
        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("x")) {
            this.givenUp = true;
            return true;
        }

        int x;
        int y;

        switch (choice.toLowerCase()) {
            case "e":
                x = this.saveIntWithRange(scanner, "x: ", 0, this.width - 1);
                y = this.saveIntWithRange(scanner, "y: ", 0, this.height - 1);
                exploreBox(x, y);
                return true;
            case "m":
                x = this.saveIntWithRange(scanner, "x: ", 0, this.width - 1);
                y = this.saveIntWithRange(scanner, "y: ", 0, this.height - 1);
                mark(x, y);
                return true;
            case "u":
                x = this.saveIntWithRange(scanner, "x: ", 0, this.width - 1);
                y = this.saveIntWithRange(scanner, "y: ", 0, this.height - 1);
                unmark(x, y);
                return true;
            default:
                System.out.println("Invalid input. Enter x for exit.\n");
                return false;
        }
    }

    public void display() {
        System.out.println("\n\n");
        System.out.print("     ");
        for (int i = 0; i < width; i++) {
            System.out.print(i + "     ");
        }
        System.out.println("\n");

        for (int i = 0; i < height; i++) {
            System.out.print(i + "    ");
            for (int j = 0; j < width; j++) {
                System.out.print(fields[i][j].getAppearance() + "     ");
            }
            System.out.println("\n");
        }
    }

    // Private Methods
    private void setupMap(int minedPossibility) {
        this.fields = new Box[this.width][this.height];
        SecureRandom rnd = new SecureRandom();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (rnd.nextInt(minedPossibility) + 1 == minedPossibility) {
                    fields[i][j] = new Box(true);
                    minedFields++;
                } else {
                    fields[i][j] = new Box(false);
                }
            }
        }
    }

    private int[][] getNearbyBoxes(int x, int y) {
        int[][] nearbyBoxes = new int[][]{
                {x - 1  , y - 1}, {x - 1 , y    }, {x - 1  , y + 1},
                {x      , y - 1},                  {x      , y + 1},
                {x + 1  , y - 1}, {x + 1 , y    }, {x + 1  , y + 1}
        };

        int numberOfValidCoords = 0;
        int[] indices = new int[8];
        int indicesIndex = 0;
        for (int i = 0; i < 8; i++) {
            if (nearbyBoxes[i][0] > -1 && nearbyBoxes[i][0] < this.height
                    && nearbyBoxes[i][1] > -1 && nearbyBoxes[i][1] < this.width) {
                numberOfValidCoords++;
                indices[indicesIndex] = i;
                indicesIndex++;
            }
        }

        int[][] validCoords = new int[numberOfValidCoords][2];
        for (int i = 0; i < numberOfValidCoords; i++) {
            validCoords[i] = nearbyBoxes[indices[i]];
        }

        return validCoords;
    }

    private int saveIntWithRange(Scanner scanner, String label, int minValue, int maxValue) {
        while (true) {
            int input = this.saveInt(label, scanner);
            if (input <= maxValue && input >= minValue) {
                return input;
            } else {
                System.out.println("The input has to be between " + minValue + " and " + maxValue + ".");
            }
        }
    }

    private int saveInt(String label, Scanner scanner) {
        while (true) {
            try {
                System.out.print(label);
                return Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException var2) {
                System.out.println("Not a valid number.");
            }
        }
    }
}

