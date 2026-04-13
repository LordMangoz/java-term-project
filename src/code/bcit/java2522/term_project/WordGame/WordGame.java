package bcit.java2522.term_project.WordGame;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.random.RandomGenerator;

/**
 * Runs the WordGame program.
 * Generates random geography questions based on countries stored in World.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class WordGame
{
    private static final int NUM_QUESTIONS = 10;
    private static final int FIRST_INDEX = 0;
    private static final int FACT_COUNT = 3;
    private static final int QUESTION_TYPE_COUNT = 3;
    private static final int QUESTION_AND_ANSWER_OFFSET = 2;
    private static final int ANSWER_OFFSET = 1;

    private static final String SCORE_FILE_NAME = "score.txt";
    private static final String YES_INPUT = "yes";
    private static final String NO_INPUT = "no";

    private static List<String> countryKeys;
    private static World world;
    private static Score score;
    private static LocalDateTime dateTimePlayed;

    private static int numGamesPlayed;
    private static int numCorrectFirstAttempt;
    private static int numCorrectSecondAttempt;
    private static int numIncorrectTwoAttempts;

    /**
     * Runs the WordGame.
     *
     * @param scan the Scanner used for user input
     */
    public static void wordGame(final Scanner scan)
    {
        dateTimePlayed = LocalDateTime.now();
        numGamesPlayed = 0;
        numCorrectFirstAttempt = 0;
        numCorrectSecondAttempt = 0;
        numIncorrectTwoAttempts = 0;

        world = new World();

        countryKeys = new ArrayList<>(world.getCountryHashMap().size());
        countryKeys.addAll(world.getCountryHashMap().keySet());

        System.out.println("Welcome to Word Game!");
        wordGameMenu(scan);
    }

    /*
     * Gets the user's guess input.
     */
    private static String getUserGuess(final Scanner scan)
    {
        return scan.nextLine().trim();
    }

    /*
     * Generates a set of random country keys to use as questions.
     */
    private static Set<String> getCountriesKeys()
    {
        final RandomGenerator random;
        final Set<String> questionKeys;

        random = RandomGenerator.getDefault();
        questionKeys = new HashSet<>();

        while (questionKeys.size() < NUM_QUESTIONS)
        {
            final int index;

            index = random.nextInt(FIRST_INDEX, countryKeys.size());
            questionKeys.add(countryKeys.get(index));
        }

        return questionKeys;
    }

    /*
     * Generates an array containing alternating questions and answers.
     */
    private static String[] generateQuestion(final Set<String> selectedCountryKeys)
    {
        final RandomGenerator random;
        final Map<String, Country> countries;
        final List<String> questionsAndAnswers;

        random = RandomGenerator.getDefault();
        countries = world.getCountryHashMap();
        questionsAndAnswers = new ArrayList<>();

        selectedCountryKeys.forEach(countryName ->
        {
            final int questionType;
            final int factIndex;

            factIndex = random.nextInt(FIRST_INDEX, FACT_COUNT);
            questionType = random.nextInt(FIRST_INDEX, QUESTION_TYPE_COUNT);

            switch (questionType)
            {
                case 0 ->
                {
                    questionsAndAnswers.add("Which country is "
                            + countries.get(countryName).getCapitalCityName()
                            + " a capital?");
                    questionsAndAnswers.add(countryName);
                }
                case 1 ->
                {
                    questionsAndAnswers.add("What is the capital of " + countryName + "?");
                    questionsAndAnswers.add(countries.get(countryName).getCapitalCityName());
                }
                case 2 ->
                {
                    questionsAndAnswers.add("Which country is this fact about?\t\t"
                            + countries.get(countryName).getFact(factIndex));
                    questionsAndAnswers.add(countryName);
                }
                default ->
                {
                }
            }
        });

        return questionsAndAnswers.toArray(String[]::new);
    }

    /*
     * Runs one full WordGame session of NUM_QUESTIONS questions.
     */
    private static void startWordGame(final Scanner scan)
    {
        final String[] questionsAndAnswers;
        int questionNumber;

        questionsAndAnswers = generateQuestion(getCountriesKeys());
        questionNumber = 1;

        for (int i = FIRST_INDEX; i < questionsAndAnswers.length; i += QUESTION_AND_ANSWER_OFFSET)
        {
            final String currentQuestion;
            final String correctAnswer;
            String guess;

            currentQuestion = "Question " + questionNumber + ": " + questionsAndAnswers[i];
            correctAnswer = questionsAndAnswers[i + ANSWER_OFFSET];
            questionNumber++;

            System.out.println(currentQuestion);

            guess = getUserGuess(scan);

            if (guess.equalsIgnoreCase(correctAnswer))
            {
                System.out.println("Correct!");
                numCorrectFirstAttempt++;
            }
            else
            {
                System.out.println("Incorrect, try again!");
                System.out.println(currentQuestion);

                guess = getUserGuess(scan);

                if (guess.equalsIgnoreCase(correctAnswer))
                {
                    System.out.println("Correct!");
                    numCorrectSecondAttempt++;
                }
                else
                {
                    System.out.println("The correct answer was " + correctAnswer + ".");
                    numIncorrectTwoAttempts++;
                }
            }
        }

        printResults();
    }

    /*
     * Prints the results of the WordGame session.
     */
    private static void printResults()
    {
        final StringBuilder resultBuilder;

        resultBuilder = new StringBuilder();

        if (numGamesPlayed == 1)
        {
            resultBuilder.append(numGamesPlayed).append(" word game played\n");
        }
        else
        {
            resultBuilder.append(numGamesPlayed).append(" word games played\n");
        }

        resultBuilder.append(numCorrectFirstAttempt)
                .append(" correct answers on the first attempt\n");

        resultBuilder.append(numCorrectSecondAttempt)
                .append(" correct answers on the second attempt\n");

        resultBuilder.append(numIncorrectTwoAttempts)
                .append(" incorrect answers on two attempts each\n\n");

        System.out.println(resultBuilder);
    }

    /*
     * Displays the WordGame menu and handles replay logic.
     */
    private static void wordGameMenu(final Scanner scan)
    {
        while (true)
        {
            final String input;

            if (numGamesPlayed == 0)
            {
                System.out.println("Play game? (Yes/No)");
            }
            else
            {
                System.out.println("Play again? (Yes/No)");
            }

            input = scan.nextLine().trim().toLowerCase();

            if (input.isEmpty())
            {
                continue;
            }

            switch (input)
            {
                case YES_INPUT ->
                {
                    numGamesPlayed++;
                    startWordGame(scan);
                }
                case NO_INPUT ->
                {
                    exitWorldGame();
                    return;
                }
                default ->
                {
                    System.out.println("Invalid Input: '" + input + "' try again.");
                }
            }
        }
    }

    /*
     * Ends the WordGame session, saves the score, and checks high score.
     */
    private static void exitWorldGame()
    {
        final List<Score> scores;
        Score highestScore;

        score = new Score(dateTimePlayed,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts);

        try
        {
            scores = Score.readScoresFromFile(SCORE_FILE_NAME);

            if (scores.isEmpty())
            {
                highestScore = score;
            }
            else
            {
                highestScore = scores.getFirst();

                for (final Score currScore : scores)
                {
                    final double currAvg;
                    final double bestAvg;

                    currAvg = (double) currScore.getScore() / currScore.getNumGamesPlayed();
                    bestAvg = (double) highestScore.getScore() / highestScore.getNumGamesPlayed();

                    if (currAvg > bestAvg)
                    {
                        highestScore = currScore;
                    }
                }
            }

            final double newAvg;
            final double highAvg;

            newAvg = (double) score.getScore() / score.getNumGamesPlayed();
            highAvg = (double) highestScore.getScore() / highestScore.getNumGamesPlayed();

            if (newAvg > highAvg)
            {
                System.out.printf(
                        "CONGRATULATIONS! You are the new high score with an average of %.2f points per game; "
                                + "the previous record was %.2f points per game on %s%n",
                        newAvg,
                        highAvg,
                        highestScore.getDateTimePlayed().format(Score.getFormatter())
                );
            }
            else
            {
                System.out.printf(
                        "You did not beat the high score of %.2f points per game from %s%n",
                        highAvg,
                        highestScore.getDateTimePlayed().format(Score.getFormatter())
                );
            }

            Score.appendScoreToFile(score, SCORE_FILE_NAME);
        }
        catch (final IOException e)
        {
            System.out.println("Error: could not write score to file.");
        }
    }
}