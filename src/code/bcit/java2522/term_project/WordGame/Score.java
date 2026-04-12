package bcit.java2522.term_project.WordGame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class Score {


    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;
    private String dateTimePlayed;

    Score()
    {
        dateTimePlayed = getFormattedTime();
    }

    /**
     * @param score
     * @param scoreFile
     */
    public void appendScoreToFile(Score score, String scoreFile) {

    }

    public String getFormattedTime()
    {
        final LocalDateTime currentTime;
        final DateTimeFormatter formatter;
        final String formattedDateTime;

        currentTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);
        return formattedDateTime;
    }



    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    public void setNumGamesPlayed(int numGamesPlayed) {
        this.numGamesPlayed = numGamesPlayed;
    }


    public int getNumCorrectFirstAttempt() {
        return numCorrectFirstAttempt;
    }

    public void setNumCorrectFirstAttempt(final int numCorrectFirstAttempt) {
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
    }


    public void incrementNumCorrectFirstAttempt()
    {
        numCorrectFirstAttempt++;
    }


    public int getNumCorrectSecondAttempt() {
        return numCorrectSecondAttempt;
    }

    public void setNumCorrectSecondAttempt(final int numCorrectSecondAttempt) {
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
    }

    public void incrementNumCorrectSecondAttempt() {
        this.numCorrectSecondAttempt += incrementingValue;
    }


    public int getNumIncorrectTwoAttempts() {
        return numIncorrectTwoAttempts;
    }

    public void setNumIncorrectTwoAttempts(int numIncorrectTwoAttempts) {
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }
    public void incrementNumIncorrectTwoAttempts() {
        this.numIncorrectTwoAttempts += incrementingValue;
    }

    public String getdateTimePlayed ()
    {
        return dateTimePlayed;
    }


}
