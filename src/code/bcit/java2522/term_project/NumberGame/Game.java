package bcit.java2522.term_project;

import bcit.java2522.term_project.NumberGame.Playable;

/**
 * Represents a generic game.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public abstract class Game implements Playable
{
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;

    public Game()
    {
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
    }

    public final void incrementGamesPlayed()
    {
        gamesPlayed++;
    }

    public final void incrementGamesWon()
    {
        gamesWon++;
    }

    public final void incrementGamesLost()
    {
        gamesLost++;
    }

    public final String getSummary()
    {
        return "Played: " + gamesPlayed +
                " Won: " + gamesWon +
                " Lost: " + gamesLost;
    }
}