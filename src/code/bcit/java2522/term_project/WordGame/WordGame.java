package bcit.java2522.term_project.WordGame;

import java.util.*;
import java.util.random.RandomGenerator;

/**
 * Word game
 */
public class WordGame
{
    static final int NUM_QUESTIONS = 10;
    static List<String> countryKeys;
    static int numGamesPlayed;
    static int numCorrectFirstAttempt;
    static int numCorrectSecondAttempt;
    static int numIncorrectTwoAttempts;

    public static void wordGame()
    {
        final String dateTimePlayed;
        final World world;
        final Score score;

        numGamesPlayed = 0;
        numCorrectFirstAttempt = 0;
        numCorrectSecondAttempt = 0;
        numIncorrectTwoAttempts = 0;

        world = new World();
        score = new Score();
        dateTimePlayed = score.getFormattedTime();


        System.out.println("WorldGame Begins! \n");
        countryKeys = new ArrayList<>(world.getCountryHashMap().size());
        countryKeys.addAll(world.getCountryHashMap().keySet());


        startWordGame(world, score);

    }


    private static void startWordGame(final World world, final Score score)
    {
        final Scanner scan;
        final String[] questionsAndAnswer;
        String guess;



        scan =  new Scanner(System.in);
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
                score.incrementNumCorrectFirstAttempt();

            }
            else
            {
                System.out.println("Incorrect, try again!");
                System.out.println(currentQuestion);
                guess = getUserGuess(scan);
                if (guess.trim().equalsIgnoreCase(questionsAndAnswer[i+1]))
                {
                    System.out.println("Correct!");
                    score.incrementNumCorrectSecondAttempt();
                }
                else
                {
                    score.incrementNumIncorrectTwoAttempts();
                    System.out.println("The correct answer was " + questionsAndAnswer[i+1] + ".");
                }
            }
        }
    }

    private static String getUserGuess(final Scanner scan)
    {
            if (!scan.hasNextLine()) return "";
            return scan.nextLine().trim();

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
}
