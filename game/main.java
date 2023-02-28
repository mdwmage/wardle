
// Imports
import java.util.*;
import java.lang.*;
import java.io.*;

public class main {
    // World-Wide Setup
    public static Scanner scan = new Scanner(System.in);
    // Colours
    public static String CORRECT = "\u001B[32m";
    public static String MISPLACED = "\u001B[33m";
    public static String WRONG = "\u001B[31m";
    public static String RESET = "\u001B[0m";
    public static String BOLD = "\u001B[1m";
    public static String PROMPT = "\u001B[34m";
    // Functions
    public static void introduction(String chosen) {
        System.out.println("Welcome to Wardle, a very exciting game\nYou have six attempts to guess a five letter word.");
        System.out.println("Green is " + CORRECT + "Correct!");
        System.out.println(RESET + "Yellow is " + MISPLACED + "Misplaced!");
        System.out.println(RESET + "Red is " + WRONG + "Wrong!" + RESET);
        linebreak(25);
        //System.out.println(chosen);
        System.out.println("Good luck!");
    }
    public static void gameloop(String wardl) {
        boolean correct = false;
        String playerInput;
        int round = 0;
        String[] guesses = new String[6];
        for (; round < 6; round++) {
            playerInput = prompt(round);
            guesses[round] = playerInput;
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
        if (win) {
            System.out.print("You win! Congrats on guessing the word! You won in ");
            if ((round+1) == 1) {
                System.out.print((round+1) + " round! Thats very exciting!");
            } else {
               System.out.print((round+1) + " rounds! Nice work!");
            }
        } else {
            System.out.println("You lost! The word was: " + wardl);
        }
    }
    public static void main(String[] args) throws Exception {
        // Variable setup
        String chosen;
        // Set up the Word
        chosen = wardlGet();
        /*For debugging, use: chosen = "fleck";*/
        // Introduction
        introduction(chosen);
        // game runs
        gameloop(chosen);
    }
    public static void linebreak(int rep) {
        for (int i = 0; i < rep; i++) {
            System.out.print("*");
        }
        System.out.println("");
    }
    public static String prompt(int round) {
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
        File words = new File("src/wardlWords.txt");
        int j = 0;
        // Parse File
        Scanner reader = new Scanner(words);
        while (reader.hasNextLine()) {
            wardles[j] = reader.nextLine();
            j++;
        }
        // Output
        output = wardles[rand.nextInt(wardles.length)];
        while (output == "null") {
            output = wardles[rand.nextInt(wardles.length)];
        }
        // Cleanup
        reader.close();;
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
                testwardle.replace(i, (i+1), " ");
                right++;
            } else if (testwardle.toString().contains(String.valueOf(input.charAt(i)))) {
                System.out.print(MISPLACED + input.charAt(i) + RESET);
                testwardle.replace((testwardle.indexOf(String.valueOf(input.charAt(i)))), (testwardle.indexOf(String.valueOf(input.charAt(i)))+1), " ");
                misplaced++;
            } else {
                System.out.print(WRONG + input.charAt(i) + RESET);
            }
        }
        System.out.println("");
        System.out.println(PROMPT + BOLD + "~ " + RESET + BOLD + right + RESET + CORRECT+" Right" + RESET + ", "+ BOLD + misplaced + RESET + MISPLACED + " Misplaced" + RESET + ", " + BOLD + (5 - (right+misplaced)) + RESET + WRONG + " Wrong" + RESET + PROMPT + BOLD + " ~" + RESET);
    }
}