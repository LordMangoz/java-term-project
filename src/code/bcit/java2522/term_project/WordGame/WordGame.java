package bcit.java2522.term_project.WordGame;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.random.RandomGenerator;

/**
 * Word game
 */
public class WordGame
{
    private static final int NUM_QUESTIONS = 10;
    //need to take in yes or no for these ones btw not y or n
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
     * Runs the WordGame file.
     * @param scan is the scanner object.
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

    private static String getUserGuess(final Scanner scan)
    {
        return scan.nextLine().trim();
    }

    private static Set<String> getCountriesKeys(final World world)
    {
        RandomGenerator random;
        random = RandomGenerator.getDefault();
        final Set<String> questionKeys;
        questionKeys = new HashSet<>();

        int index;
        while(questionKeys.size() < NUM_QUESTIONS)
        {
            index = random.nextInt(0, countryKeys.size());
            questionKeys.add(countryKeys.get(index));
        }
        return questionKeys;
    }
    private static String[] generateQuestion(final World world, final Set<String> countryKeys)
    {
        final RandomGenerator random;
        final Map<String, Country> countries;
        final List<String> questionsAndAnswers;

        random = RandomGenerator.getDefault();
        countries = world.getCountryHashMap();
        questionsAndAnswers = new ArrayList<>();

        countryKeys.forEach( countryName ->{
            final int questionType;
            final int factIndex;

            factIndex = random.nextInt(0,3);
            questionType = random.nextInt(0,3);

            switch(questionType) {
                case 0 -> {
                    questionsAndAnswers.add("Which country is " + countries.get(countryName).getCapitalCityName() + " a capital?");
                    questionsAndAnswers.add(countryName);
                }
                case 1 -> {
                    questionsAndAnswers.add("What is the capital of "+ countryName + "?");
                    questionsAndAnswers.add(countries.get(countryName).getCapitalCityName());
                }
                case 2 -> {
                    questionsAndAnswers.add("Which country is this fact about?\t\t" + countries.get(countryName).getFact(factIndex));
                    questionsAndAnswers.add(countryName);
                }
            }
        });
        return questionsAndAnswers.toArray(String[]::new);
    }

    private static void startWordGame(final World world, final Scanner scan)
    {
        final String[] questionsAndAnswer;
        String guess;

        questionsAndAnswer = generateQuestion(world, getCountriesKeys(world));

        String currentQuestion;
        int questionNumber;
        questionNumber = 1;

        for (int i = 0; i < questionsAndAnswer.length; i+=2)
        {
            currentQuestion = "Question "+ (questionNumber++) + ": " + questionsAndAnswer[i];
            System.out.println(currentQuestion);

            guess = getUserGuess(scan);

            if (guess.trim().equalsIgnoreCase(questionsAndAnswer[i+1]))
            {
                System.out.println("Correct!");
                numCorrectFirstAttempt++;

            }
            else
            {
                System.out.println("Incorrect, try again!");
                System.out.println(currentQuestion);
                guess = getUserGuess(scan);
                if (guess.trim().equalsIgnoreCase(questionsAndAnswer[i+1]))
                {
                    System.out.println("Correct!");
                    numCorrectSecondAttempt++;
                }
                else
                {
                    System.out.println("The correct answer was " + questionsAndAnswer[i+1] + ".");
                    numIncorrectTwoAttempts++;
                }
            }
        }
        printResults();


    }

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

        resultBuilder.append(numCorrectFirstAttempt).append(" correct answers on the first attempt\n");

        resultBuilder.append(numCorrectSecondAttempt).append(" correct answers on the second attempt\n");

        resultBuilder.append(numIncorrectTwoAttempts).append(" incorrect answers on two attempts each\n\n");

        System.out.println(resultBuilder);

    }

    private static void wordGameMenu(final Scanner scan)
    {
        boolean stopSignal;
        String userInput;
        String input;

        while (true) {
            if ( numGamesPlayed == 0)
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
            userInput = input;

            switch (userInput) {
                case YES_INPUT -> {
                    numGamesPlayed++;
                    startWordGame(world, scan);
                }
                case NO_INPUT -> {
                    exitWorldGame();
                    return;
                }
                default -> {
                    System.out.println("Invalid Input: '" + userInput + "' try again.");
                }
            }
        }
    }

    private static void exitWorldGame() {
        // TODO: append to score file + check high score
        //create score object
        List<Score> scores;
        Score highestScore;

        score = new Score(dateTimePlayed,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts);

        try {
            scores = Score.readScoresFromFile(SCORE_FILE_NAME);

            if (scores.isEmpty()) {
                highestScore = score;
            } else {
                highestScore = scores.getFirst();

                for (final Score currScore : scores) {
                    final double currAvg;
                    final double bestAvg;

                    currAvg = (double) currScore.getScore() / currScore.getNumGamesPlayed();
                    bestAvg = (double) highestScore.getScore() / highestScore.getNumGamesPlayed();

                    if (currAvg > bestAvg) {
                        highestScore = currScore;
                    }
                }
            }

            final double newAvg;
            final double highAvg;

            newAvg = (double) score.getScore() / score.getNumGamesPlayed();
            highAvg = (double) highestScore.getScore() / highestScore.getNumGamesPlayed();

            if (newAvg > highAvg) {
                System.out.printf(
                        "CONGRATULATIONS! You are the new high score with an average of %.2f points per game; the previous record was %.2f points per game on %s%n",
                        newAvg,
                        highAvg,
                        highestScore.getDateTimePlayed().format(Score.getFormatter())
                );
            } else {
                System.out.printf(
                        "You did not beat the high score of %.2f points per game from %s%n",
                        highAvg,
                        highestScore.getDateTimePlayed().format(Score.getFormatter())
                );
            }

            Score.appendScoreToFile(score, SCORE_FILE_NAME);
        } catch (final IOException e) {
            System.out.println("Error: could not write score to file.");
        }
    }
}
