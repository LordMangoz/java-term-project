package bcit.java2522.term_project.NumberGame;

/**
 * Represents games that track wins and losses.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public abstract class Game implements Playable
{
    /** Default starting value for counters. */
    private static final int DEFAULT_COUNTER_VALUE = 0;

    /* Total games played. */
    private int gamesPlayed;

    /* Total games won. */
    private int gamesWon;

    /* Total games lost. */
    private int gamesLost;

    /**
     * Constructs a Game.
     */
    public Game()
    {
        gamesPlayed = DEFAULT_COUNTER_VALUE;
        gamesWon = DEFAULT_COUNTER_VALUE;
        gamesLost = DEFAULT_COUNTER_VALUE;
    }

    /**
     * Records a win and increments games played.
     */
    public final void recordWin()
    {
        gamesPlayed++;
        gamesWon++;
    }

    /**
     * Records a loss and increments games played.
     */
    public final void recordLoss()
    {
        gamesPlayed++;
        gamesLost++;
    }

    /**
     * Gets the number of games played.
     *
     * @return games played
     */
    public final int getGamesPlayed()
    {
        return gamesPlayed;
    }

    /**
     * Gets the number of games won.
     *
     * @return games won
     */
    public final int getGamesWon()
    {
        return gamesWon;
    }

    /**
     * Gets the number of games lost.
     *
     * @return games lost
     */
    public final int getGamesLost()
    {
        return gamesLost;
    }
}