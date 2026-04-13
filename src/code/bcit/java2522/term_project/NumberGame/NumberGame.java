package bcit.java2522.term_project.NumberGame;


import java.util.random.RandomGenerator;

/**
 * Represents the NumberGame logic.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class NumberGame extends Game
{
    /** Total number of slots in the grid. */
    private static final int GRID_SIZE = 20;

    /** Minimum number that can be generated. */
    private static final int MIN_NUMBER = 1;

    /** Maximum number that can be generated. */
    private static final int MAX_NUMBER = 1000;

    private static final int EMPTY_SLOTS = 0;
    private static final int FIRST_INDEX = 0;
    private static final int NEXT_INDEX_OFFSET = 1;

    /** Random number source. */
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final int NO_GAMES_PLAYED = 0;

    /* Current number that must be placed. */
    private int currentNumber;

    /* Number of filled slots in the current round. */
    private int filledSlots;

    /* Total successful placements across all rounds. */
    private int totalPlacements;

    /* Tracks whether the current round has ended. */
    private boolean gameEnded;

    /* Stores the placed numbers. Null means empty slot. */
    private final Integer[] placedNumbers;

    /**
     * Constructs a NumberGame.
     */
    public NumberGame()
    {
        placedNumbers = new Integer[GRID_SIZE];
        resetGame();
    }

    /**
     * Resets the game state for a new round.
     */
    public void resetGame()
    {
        int index;

        gameEnded = false;
        filledSlots = EMPTY_SLOTS;

        for (index = FIRST_INDEX; index < GRID_SIZE; index++)
        {
            placedNumbers[index] = null;
        }

        generateNextNumber();
    }

    /**
     * Generates the next random number.
     */
    public void generateNextNumber()
    {
        currentNumber = RANDOM.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
    }

    /**
     * Gets the current number.
     *
     * @return the current number
     */
    public int getCurrentNumber()
    {
        return currentNumber;
    }

    /**
     * Gets the placed numbers.
     *
     * @return the placed numbers array
     */
    public Integer[] getPlacedNumbers()
    {
        return placedNumbers;
    }

    /**
     * Attempts to place the current number at the given index.
     *
     * @param index the grid index
     * @return true if the placement succeeded
     */
    public boolean placeNumber(final int index)
    {
        if (index < FIRST_INDEX ||
            index >= GRID_SIZE)
        {
            return false;
        }

        if (placedNumbers[index] != null)
        {
            return false;
        }

        if (!isLegalPlacement(index, currentNumber))
        {
            return false;
        }

        placedNumbers[index] = currentNumber;
        filledSlots++;
        totalPlacements++;

        if (filledSlots == GRID_SIZE)
        {
            recordWin();
            gameEnded = true;
        }

        return true;
    }

    /**
     * Checks whether the current round is won.
     *
     * @return true if the round is won
     */
    public boolean isGameWon()
    {
        return filledSlots == GRID_SIZE;
    }

    /**
     * Checks if there is a legal placement for the current number.
     * @return true if a legal move exists.
     */
    public boolean hasLegalMove()
    {
        for (int i = 0; i < GRID_SIZE; i++)
        {
            if (placedNumbers[i] == null &&
                isLegalPlacement(i, currentNumber))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether placing a value at the given index preserves order.
     *
     * @param index the grid index
     * @param value the value to test
     *
     * @return true if the placement is legal
     */
    public boolean isLegalPlacement(final int index, final int value)
    {
        int leftIndex;
        int rightIndex;

        leftIndex = index - NEXT_INDEX_OFFSET;
        while (leftIndex >= FIRST_INDEX &&
                placedNumbers[leftIndex] == null)
        {
            leftIndex--;
        }

        if (leftIndex >= FIRST_INDEX &&
            placedNumbers[leftIndex] > value)
        {
            return false;
        }

        rightIndex = index + NEXT_INDEX_OFFSET;

        while (rightIndex < GRID_SIZE &&
            placedNumbers[rightIndex] == null)
        {
            rightIndex++;
        }

        return rightIndex >= GRID_SIZE ||
               placedNumbers[rightIndex] >= value;
    }


    /**
     * Builds the score summary text.
     *
     * @return the score summary
     */
    public String buildScoreText()
    {
        final double average;
        final int gamesPlayed;

        gamesPlayed = getGamesPlayed();

        if (gamesPlayed == NO_GAMES_PLAYED)
        {
            average = NO_GAMES_PLAYED;
        }
        else
        {
            average = (double) totalPlacements / gamesPlayed;
        }

        return "You won " + getGamesWon() + " out of " + gamesPlayed +
                " games and you lost " + getGamesLost() + " out of " + gamesPlayed +
                " games, with " + totalPlacements +
                " successful placements, an average of " + average + " per game.";
    }


    /**
     * Starts the game.
     */
    @Override
    public void play()
    {
        resetGame();
    }

    /**
     * Ends the game.
     */
    @Override
    public void stopPlaying()
    {
        if (!gameEnded)
        {
            recordLoss();
            gameEnded = true;
        }
    }

}