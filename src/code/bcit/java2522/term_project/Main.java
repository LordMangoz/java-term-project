package bcit.java2522.term_project;

import bcit.java2522.term_project.NumberGame.NumberGame;
import bcit.java2522.term_project.NumberGame.NumberGameFx;
import bcit.java2522.term_project.WordGame.WordGame;
import bcit.java2522.term_project.MyGame.MyGame;

import java.util.Scanner;

/**
 * Main class runs 3 games,
 *  1. myGame,
 *  2. Numbergame,
 *  3. WordGame.
 *
 * @author Umanga Bajgai
 *
 * @version 1.0
 */
public class Main {
    private static Scanner scan;
    private static final char wordGameInput = 'w';;
    private static final char numberGameInput = 'n';
    private static final char nameOfGameHereInput = 'm';;
    private static final char quitInput = 'q';

    /**
     * Runs the program.
     * @param args unused
     */

    public static void main(final String[] args)
    {
        scan = new Scanner(System.in);

        menu();

        scan.close();

    }

    private static void menu()
    {
        boolean stopSignal;
        stopSignal = false;

        while (!stopSignal)
        {
            System.out.println("------ menu ------");
            System.out.println("Press W to play the Word game.");
            System.out.println("Press N to play the Number game.");
            System.out.println("Press M to play the nameOfGameHere.");
            System.out.println("Press Q to quit.");
            System.out.println("------------------");

            String rawInput;
            char userInput;
            rawInput =  scan.nextLine().trim().toLowerCase();
            if (rawInput.isEmpty())
            {
                continue;
            }
            userInput = rawInput.charAt(0);

            switch (userInput)
            {
                case wordGameInput -> WordGame.wordGame(scan);
                case numberGameInput -> NumberGameFx.numberGame();
                case nameOfGameHereInput -> MyGame.myGame(scan);
                case quitInput -> {
                    System.out.println("Thanks for playing!");
                    stopSignal = true;
                }
                default -> System.out.println("Invalid Input: '" + userInput + "' try again.");
            }
        }
    }
}
