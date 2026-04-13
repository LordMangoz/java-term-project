package bcit.java2522.term_project.WordGame;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Score handles
 *
 * @author Umanga Bagai
 *
 * @version 1.0
 */
public class Score {

    private static final DateTimeFormatter FORMATTER;
    private static final int FIRST_ATTEMPT_POINTS;
    private static final int SECOND_ATTEMPT_POINTS;

    static
    {
        FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        FIRST_ATTEMPT_POINTS = 2;
        SECOND_ATTEMPT_POINTS = 1;
    }
    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;
    private final LocalDateTime dateTimePlayed;



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

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    public int getScore()
    {
        return (numCorrectFirstAttempt * FIRST_ATTEMPT_POINTS)
                + (numCorrectSecondAttempt * SECOND_ATTEMPT_POINTS);
    }

    public LocalDateTime getDateTimePlayed() {
        return dateTimePlayed;
    }

    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }


    public int getNumCorrectFirstAttempt() {
        return numCorrectFirstAttempt;
    }


    public int getNumCorrectSecondAttempt()
    {
        return numCorrectSecondAttempt;
    }



    public int getNumIncorrectTwoAttempts()
    {
        return numIncorrectTwoAttempts;
    }



    /**
     * @param score represents the score instance to be appended.
     * @param scoreFile represents the file to append to.
     *

     */
    public static void appendScoreToFile(final Score score,
                                         final String scoreFile) throws IOException
    {
        try (FileWriter writer = new FileWriter(scoreFile, true))
        {
            writer.write(score.toString());
        }
    }


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

                dateTimeString = lineDateTime.replace("Date and Time: ", "").trim();
                dateTimePlayed = LocalDateTime.parse(dateTimeString, FORMATTER);

                numGamesPlayed = Integer.parseInt(lineGamesPlayed.replace("Games Played: ", "").trim());
                numCorrectFirstAttempt = Integer.parseInt(lineCorrectFirst.replace("Correct First Attempts: ", "").trim());
                numCorrectSecondAttempt = Integer.parseInt(lineCorrectSecond.replace("Correct Second Attempts: ", "").trim());
                numIncorrectTwoAttempts = Integer.parseInt(lineIncorrect.replace("Incorrect Attempts: ", "").trim());

                scores.add(new Score(dateTimePlayed,
                        numGamesPlayed,
                        numCorrectFirstAttempt,
                        numCorrectSecondAttempt,
                        numIncorrectTwoAttempts));
            }
        }

        return scores;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Date and Time: %s\nGames Played: %d\nCorrect First Attempts: %d\nCorrect Second Attempts: %d\nIncorrect Attempts: %d\nScore: %d points\n",
                dateTimePlayed.format(FORMATTER),
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts,
                getScore()
        );
    }

}
