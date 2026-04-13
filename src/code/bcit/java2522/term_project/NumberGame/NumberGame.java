package bcit.java2522.term_project.NumberGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.random.RandomGenerator;

/**
 * NumberGame is a JavaFX game where the user must place 20 random numbers
 * into a 4x5 grid in ascending order.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class NumberGame extends Application
{
    private static final int ROWS;
    private static final int COLS;
    private static final int GRID_SIZE;

    private static final int MIN_NUMBER;
    private static final int MAX_NUMBER;

    private static final int WINDOW_WIDTH_PX;
    private static final int WINDOW_HEIGHT_PX;

    private static final int BUTTON_WIDTH_PX;
    private static final int BUTTON_HEIGHT_PX;

    private static final String WINDOW_TITLE;
    private static final String EMPTY_SLOT_TEXT;

    private static final String TRY_AGAIN_TEXT;
    private static final String QUIT_TEXT;

    private static final RandomGenerator RANDOM;

    private static boolean javafxStarted;

    static
    {
        ROWS = 4;
        COLS = 5;
        GRID_SIZE = ROWS * COLS;

        MIN_NUMBER = 1;
        MAX_NUMBER = 1000;

        WINDOW_WIDTH_PX = 600;
        WINDOW_HEIGHT_PX = 400;

        BUTTON_WIDTH_PX = 90;
        BUTTON_HEIGHT_PX = 60;

        WINDOW_TITLE = "Number Game";
        EMPTY_SLOT_TEXT = "-";

        TRY_AGAIN_TEXT = "Try Again";
        QUIT_TEXT = "Quit";

        RANDOM = RandomGenerator.getDefault();
        javafxStarted = false;
    }

    private final Integer[] placedNumbers;
    private final Button[] buttons;

    private int currentNumber;
    private int filledSlots;

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int totalPlacements;

    private Label currentNumberLabel;
    private Stage stage;

    /**
     * Constructs a NumberGame.
     */
    public NumberGame()
    {
        currentNumber = 0;
        filledSlots = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        totalPlacements = 0;
        placedNumbers = new Integer[GRID_SIZE];
        buttons = new Button[GRID_SIZE];
    }

    /**
     * Entry point into the NumberGame program.
     * Starts JavaFX once, then uses Platform.runLater after.
     */
    public static void numberGame()
    {
        if (!javafxStarted)
        {
            javafxStarted = true;
            Platform.startup(NumberGame::launchGame);
        }
        else
        {
            Platform.runLater(NumberGame::launchGame);
        }
    }

    /**
     * Starts the JavaFX stage.
     *
     * @param stage the primary stage
     */
    @Override
    public void start(final Stage stage)
    {
        final VBox root;
        final GridPane grid;
        final Scene scene;

        this.stage = stage;

        currentNumberLabel = new Label();

        grid = new GridPane();
        buildGrid(grid);

        root = new VBox();
        root.getChildren().add(currentNumberLabel);
        root.getChildren().add(grid);

        startNewRound();

        scene = new Scene(root, WINDOW_WIDTH_PX, WINDOW_HEIGHT_PX);

        stage.setOnCloseRequest(event ->
        {
            event.consume();
            exitProcedure();
        });

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the game.
     */
    private static void launchGame()
    {
        final NumberGame game;
        final Stage stage;

        game = new NumberGame();
        stage = new Stage();

        try
        {
            game.start(stage);
        }
        catch (final Exception exception)
        {
            System.out.println(exception);
        }
    }

    /**
     * Builds the 4x5 grid of buttons.
     *
     * @param grid the GridPane
     */
    private void buildGrid(final GridPane grid)
    {
        int index;
        index = 0;

        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                final int buttonIndex;
                final Button button;

                buttonIndex = index;

                button = new Button(EMPTY_SLOT_TEXT);
                button.setMinWidth(BUTTON_WIDTH_PX);
                button.setMinHeight(BUTTON_HEIGHT_PX);

                button.setOnAction(event -> placeNumber(buttonIndex));

                buttons[index] = button;
                grid.add(button, col, row);

                index++;
            }
        }
    }

    /**
     * Starts a new game round.
     */
    private void startNewRound()
    {
        filledSlots = 0;

        for (int i = 0; i < GRID_SIZE; i++)
        {
            placedNumbers[i] = null;
            buttons[i].setText(EMPTY_SLOT_TEXT);
            buttons[i].setDisable(false);
        }

        generateNextNumber();
        refreshButtonStates();
    }

    /**
     * Generates the next random number.
     */
    private void generateNextNumber()
    {
        currentNumber = RANDOM.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
        currentNumberLabel.setText("Current number: " + currentNumber);
    }

    /**
     * Handles placing the number into a chosen slot.
     *
     * @param index the button index
     */
    private void placeNumber(final int index)
    {
        if (placedNumbers[index] != null)
        {
            return;
        }

        if (!isLegalPlacement(index, currentNumber))
        {
            return;
        }

        placedNumbers[index] = currentNumber;
        buttons[index].setText(String.valueOf(currentNumber));
        buttons[index].setDisable(true);

        filledSlots++;
        totalPlacements++;

        if (filledSlots == GRID_SIZE)
        {
            gamesPlayed++;
            gamesWon++;
            showEndDialog(true);
            return;
        }

        generateNextNumber();
        refreshButtonStates();

        if (!hasLegalMove())
        {
            gamesPlayed++;
            gamesLost++;
            showEndDialog(false);
        }
    }

    /**
     * Checks if the placement keeps the grid ascending.
     *
     * @param index slot index
     * @param value number being placed
     * @return true if valid
     */
    private boolean isLegalPlacement(final int index, final int value)
    {
        int leftIndex;
        int rightIndex;
        leftIndex = index - 1;
        while (leftIndex >= 0 && placedNumbers[leftIndex] == null)
        {
            leftIndex--;
        }

        if (leftIndex >= 0 && placedNumbers[leftIndex] > value)
        {
            return false;
        }

        rightIndex = index + 1;
        while (rightIndex < GRID_SIZE && placedNumbers[rightIndex] == null)
        {
            rightIndex++;
        }

        if (rightIndex < GRID_SIZE && placedNumbers[rightIndex] < value)
        {
            return false;
        }

        return true;
    }

    /**
     * Updates button enabled/disabled state depending on legal moves.
     */
    private void refreshButtonStates()
    {
        for (int i = 0; i < GRID_SIZE; i++)
        {
            if (placedNumbers[i] == null)
            {
                buttons[i].setDisable(!isLegalPlacement(i, currentNumber));
            }
        }
    }

    /**
     * Checks if there is any legal placement available.
     *
     * @return true if a move exists
     */
    private boolean hasLegalMove()
    {
        for (int i = 0; i < GRID_SIZE; i++)
        {
            if (placedNumbers[i] == null && isLegalPlacement(i, currentNumber))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Shows win/loss popup and asks retry or quit.
     *
     * @param won true if player won
     */
    private void showEndDialog(final boolean won)
    {
        final Alert alert;
        final ButtonType tryAgainButton;
        final ButtonType quitButton;
        final ButtonType choice;

        alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (won)
        {
            alert.setTitle("You Win!");
            alert.setHeaderText("You successfully placed all 20 numbers.");
        }
        else
        {
            alert.setTitle("Game Over");
            alert.setHeaderText("No valid placement exists. You lost.");
        }

        tryAgainButton = new ButtonType(TRY_AGAIN_TEXT);
        quitButton = new ButtonType(QUIT_TEXT);

        alert.getButtonTypes().setAll(tryAgainButton, quitButton);
        alert.setContentText(buildScoreText());

        choice = alert.showAndWait().orElse(quitButton);

        if (choice == tryAgainButton)
        {
            startNewRound();
        }
        else
        {
            exitProcedure();
        }
    }

    /**
     * Builds the score summary.
     *
     * @return score string
     */
    private String buildScoreText()
    {
        final double average;

        if (gamesPlayed == 0)
        {
            average = 0.0;
        }
        else
        {
            average = (double) totalPlacements / gamesPlayed;
        }

        return "You won " + gamesWon + " out of " + gamesPlayed +
                " games and you lost " + gamesLost + " out of " + gamesPlayed +
                " games, with " + totalPlacements +
                " successful placements, an average of " + average + " per game.";
    }

    /**
     * Shows final score and exits JavaFX.
     */
    private void exitProcedure()
    {
        final Alert alert;

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Score");
        alert.setHeaderText("NumberGame Summary");
        alert.setContentText(buildScoreText());
        alert.showAndWait();

        stage.close();
    }
}