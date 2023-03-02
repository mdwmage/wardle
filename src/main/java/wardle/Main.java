// Packagify
package wardle;

// Imports
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
    // File-Wide Scanner
    public static Scanner scan = new Scanner(System.in);
    // Colours
    public static String CORRECT = "\u001B[32m";
    public static String MISPLACED = "\u001B[33m";
    public static String WRONG = "\u001B[31m";
    public static String RESET = "\u001B[0m";
    public static String BOLD = "\u001B[1m";
    public static String PROMPT = "\u001B[34m";
    // Minimalistic main function
    public static void main(String[] args) throws Exception {
        game();
    }
    // Functions
    // Runs quick introduction
    public static void introduction() {
        System.out.println("Welcome to Wardle, a very exciting game.");
        System.out.println("You have six attempts to guess a five letter word.");
        System.out.println("You'll get your word back in 3 different colours!");
        System.out.println("Green is " + CORRECT + "Correct!");
        System.out.println(RESET + "Yellow is " + MISPLACED + "Misplaced!");
        System.out.println(RESET + "Red is " + WRONG + "Wrong!" + RESET);
        System.out.println("Try to guess the word before you run out of rounds!");
        linebreak(45);
    }
    // Main gameloop
    public static void gameloop(String wardl) {
        // Variable setup
        boolean correct = false; // Win-condition bool
        String playerInput;
        int round = 0;
        String[] guesses = new String[6]; // Collect strings. Currently unused
        // Main gameplay loop
        for (; round < 6; round++) {
            playerInput = prompt(round);
            guesses[round] = playerInput;
            // Check if the word is ok, this could use some work
            if (playerInput.length() == 5) {
                wardleCompare(playerInput, wardl);
                if (playerInput.equals(wardl)) {
                    correct = true;
                    break;
                }
            } else {
                System.out.println("Bad wardl!, please retry.");
                guesses[round] = "";
                round--;
            }
        }
        ending(correct, round, wardl);
    }

    public static void ending(boolean win, int round, String wardl) {
        linebreak(45);
        // Checks if you won
        if (win) {
            System.out.print("You win! Congrats on guessing the word! You won in ");
            // Checks how many rounds you won in
            if ((round + 1) == 1) {
                System.out.println((round + 1) + " round! Thats very exciting!");
            } else {
                System.out.println((round + 1) + " rounds! Nice work!");
            }
        } else {
            // Loss message
            System.out.println("You lost! The word was: " + wardl + ". Better luck next time.");
        }
    }
    // game stuffed into function for package use
    public static void game() throws Exception {
        // Variable setup
        boolean playing = true;
        String chosen;
        String input;
        // Set up the Word
        // Introduction
        introduction();
        // game runs
        while (playing) {
            System.out.println(RESET + "Type to play to play, help to get a little guide, or exit to quit the program");
            System.out.print("[" + WRONG + "MENU" + RESET + "]" + PROMPT + "> " + RESET);
            input = scan.nextLine();
            if (input.equals("play")) {
                System.out.println("Good luck!");
                linebreak(45);
                chosen = wardlGet();
                // For debugging, use: chosen = "fleck";
                gameloop(chosen);
            } else if (input.equals("exit")) {
                System.out.println("Thanks for playing!");
                playing = false;
            } else if (input.equals("help")) {
                introduction();
            } else
                System.out.println("Command not understood. Could you try retyping that?");
        }
    }

    public static void linebreak(int rep) {
        for (int i = 0; i < rep; i++) {
            System.out.print("*");
        }
        System.out.println("");
    }

    public static String prompt(int round) {
        // Pretty Prompt for player
        System.out.print("[" + PROMPT + "Round" + RESET + BOLD + ":" + RESET);
        if (round < 3) {
            System.out.print(CORRECT + (round + 1) + RESET);
        } else if (round == 3) {
            System.out.print(MISPLACED + (round + 1) + RESET);
        } else {
            System.out.print(WRONG + (round + 1) + RESET);
        }
        System.out.print("]" + PROMPT + "> " + RESET);
        return scan.nextLine();
    }

    public static String wardlGet() throws Exception {
        // Setup
        Random rand = new Random();
        String output;
        String[] wardles = new String[2315];
        InputStream inputStream = Main.class.getResourceAsStream("/wardlWords.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int j = 0;
        // Parse File
        while ((output = reader.readLine()) != null) {
            wardles[j] = output;
            j++;
        }
        // Output
        output = wardles[rand.nextInt(wardles.length)];
        while (output == "null") {
            output = wardles[rand.nextInt(wardles.length)];
        }
        // Return
        return output;
    }

    public static void wardleCompare(String input, String wardl) {
        // variable setup
        int right = 0;
        int misplaced = 0;
        StringBuilder testwardle = new StringBuilder(wardl);
        // Parse string
        for (int i = 0; i < 5; i++) {
            if (input.charAt(i) == testwardle.charAt(i)) {
                System.out.print(CORRECT + input.charAt(i) + RESET);
                testwardle.replace(i, (i + 1), " ");
                right++;
            } else if (testwardle.toString().contains(String.valueOf(input.charAt(i)))) {
                System.out.print(MISPLACED + input.charAt(i) + RESET);
                testwardle.replace((testwardle.indexOf(String.valueOf(input.charAt(i)))),
                        (testwardle.indexOf(String.valueOf(input.charAt(i))) + 1), " ");
                misplaced++;
            } else {
                System.out.print(WRONG + input.charAt(i) + RESET);
            }
        }
        System.out.println("");
        System.out.println(PROMPT + BOLD + "~ " + RESET + BOLD + right + RESET + CORRECT + " Right" + RESET + ", "
                + BOLD + misplaced + RESET + MISPLACED + " Misplaced" + RESET + ", " + BOLD + (5 - (right + misplaced))
                + RESET + WRONG + " Wrong" + RESET + PROMPT + BOLD + " ~" + RESET);
    }

}