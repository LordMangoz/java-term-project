package bcit.java2522.term_project.WordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Score record for the WordGame.
 * Stores game performance and supports file saving/loading.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class Score
{
    private static final DateTimeFormatter FORMATTER;
    private static final int FIRST_ATTEMPT_POINTS;
    private static final int SECOND_ATTEMPT_POINTS;

    private static final String DATE_TIME_LABEL;
    private static final String GAMES_PLAYED_LABEL;
    private static final String CORRECT_FIRST_LABEL;
    private static final String CORRECT_SECOND_LABEL;
    private static final String INCORRECT_LABEL;
    private static final String SCORE_LABEL;

    static
    {
        FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        FIRST_ATTEMPT_POINTS = 2;
        SECOND_ATTEMPT_POINTS = 1;

        DATE_TIME_LABEL = "Date and Time: ";
        GAMES_PLAYED_LABEL = "Games Played: ";
        CORRECT_FIRST_LABEL = "Correct First Attempts: ";
        CORRECT_SECOND_LABEL = "Correct Second Attempts: ";
        INCORRECT_LABEL = "Incorrect Attempts: ";
        SCORE_LABEL = "Score: ";
    }

    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;
    private final LocalDateTime dateTimePlayed;

    /**
     * Constructs a Score.
     *
     * @param dateTimePlayed the date and time the game was played
     * @param numGamesPlayed the number of games played
     * @param numCorrectFirstAttempt number of correct first-attempt answers
     * @param numCorrectSecondAttempt number of correct second-attempt answers
     * @param numIncorrectTwoAttempts number of incorrect answers after two attempts
     */
    public Score(final LocalDateTime dateTimePlayed,
                 final int numGamesPlayed,
                 final int numCorrectFirstAttempt,
                 final int numCorrectSecondAttempt,
                 final int numIncorrectTwoAttempts)
    {
        this.dateTimePlayed = dateTimePlayed;
        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    /**
     * Gets the formatter used for storing date and time.
     *
     * @return the date time formatter
     */
    public static DateTimeFormatter getFormatter()
    {
        return FORMATTER;
    }

    /**
     * Calculates the total score.
     *
     * @return the score
     */
    public int getScore()
    {
        return (numCorrectFirstAttempt * FIRST_ATTEMPT_POINTS)
                + (numCorrectSecondAttempt * SECOND_ATTEMPT_POINTS);
    }

    /**
     * Gets the date and time the score was recorded.
     *
     * @return the date and time played
     */
    public LocalDateTime getDateTimePlayed()
    {
        return dateTimePlayed;
    }

    /**
     * Gets the number of games played.
     *
     * @return number of games played
     */
    public int getNumGamesPlayed()
    {
        return numGamesPlayed;
    }

    /**
     * Gets the number of correct first attempts.
     *
     * @return number of correct first attempts
     */
    public int getNumCorrectFirstAttempt()
    {
        return numCorrectFirstAttempt;
    }

    /**
     * Gets the number of correct second attempts.
     *
     * @return number of correct second attempts
     */
    public int getNumCorrectSecondAttempt()
    {
        return numCorrectSecondAttempt;
    }

    /**
     * Gets the number of incorrect answers after two attempts.
     *
     * @return number of incorrect two-attempt answers
     */
    public int getNumIncorrectTwoAttempts()
    {
        return numIncorrectTwoAttempts;
    }

    /**
     * Appends a Score to the given file.
     *
     * @param score the Score instance to append
     * @param scoreFile the file path to append to
     *
     * @throws IOException if writing fails
     */
    public static void appendScoreToFile(final Score score,
                                         final String scoreFile) throws IOException
    {
        try (FileWriter writer = new FileWriter(scoreFile, true))
        {
            writer.write(score.toString());
        }
    }

    /**
     * Reads all saved scores from the given file.
     *
     * @param scoreFile the file path to read from
     * @return a list of Score objects
     *
     * @throws IOException if reading fails
     */
    public static List<Score> readScoresFromFile(final String scoreFile) throws IOException
    {
        final File file;
        final List<Score> scores;

        file = new File(scoreFile);
        scores = new ArrayList<>();

        if (!file.exists())
        {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            while (true)
            {
                final String lineDateTime;
                final String lineGamesPlayed;
                final String lineCorrectFirst;
                final String lineCorrectSecond;
                final String lineIncorrect;
                final String lineScore;

                lineDateTime = reader.readLine();

                if (lineDateTime == null)
                {
                    break;
                }

                if (lineDateTime.isBlank())
                {
                    continue;
                }

                lineGamesPlayed = reader.readLine();
                lineCorrectFirst = reader.readLine();
                lineCorrectSecond = reader.readLine();
                lineIncorrect = reader.readLine();
                lineScore = reader.readLine();

                if (lineScore == null)
                {
                    break;
                }

                final String dateTimeString;
                final LocalDateTime dateTimePlayed;

                final int numGamesPlayed;
                final int numCorrectFirstAttempt;
                final int numCorrectSecondAttempt;
                final int numIncorrectTwoAttempts;

                dateTimeString = lineDateTime.replace(DATE_TIME_LABEL, "").trim();
                dateTimePlayed = LocalDateTime.parse(dateTimeString, FORMATTER);

                numGamesPlayed = Integer.parseInt(lineGamesPlayed.replace(GAMES_PLAYED_LABEL, "").trim());
                numCorrectFirstAttempt = Integer.parseInt(lineCorrectFirst.replace(CORRECT_FIRST_LABEL, "").trim());
                numCorrectSecondAttempt = Integer.parseInt(lineCorrectSecond.replace(CORRECT_SECOND_LABEL, "").trim());
                numIncorrectTwoAttempts = Integer.parseInt(lineIncorrect.replace(INCORRECT_LABEL, "").trim());

                scores.add(new Score(dateTimePlayed,
                        numGamesPlayed,
                        numCorrectFirstAttempt,
                        numCorrectSecondAttempt,
                        numIncorrectTwoAttempts));
            }
        }

        return scores;
    }

    /**
     * Builds a formatted string representation of the Score.
     *
     * @return formatted score text
     */
    @Override
    public String toString()
    {
        return String.format(
                "%s%s\n%s%d\n%s%d\n%s%d\n%s%d\n%s%d points\n",
                DATE_TIME_LABEL,
                dateTimePlayed.format(FORMATTER),
                GAMES_PLAYED_LABEL,
                numGamesPlayed,
                CORRECT_FIRST_LABEL,
                numCorrectFirstAttempt,
                CORRECT_SECOND_LABEL,
                numCorrectSecondAttempt,
                INCORRECT_LABEL,
                numIncorrectTwoAttempts,
                SCORE_LABEL,
                getScore()
        );
    }
}