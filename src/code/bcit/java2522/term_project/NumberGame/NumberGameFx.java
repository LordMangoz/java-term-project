package bcit.java2522.term_project.NumberGame;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * JavaFX GUI for the NumberGame.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class NumberGameFx
{
    /** Number of rows in the grid. */
    private static final int ROWS = 4;

    /** Number of columns in the grid. */
    private static final int COLS = 5;

    /** Total number of buttons in the grid. */
    private static final int GRID_SIZE = ROWS * COLS;

    /** Window width in pixels. */
    private static final int WINDOW_WIDTH_PX = 600;

    /** Window height in pixels. */
    private static final int WINDOW_HEIGHT_PX = 400;

    /** Window title text. */
    private static final String WINDOW_TITLE = "Number Game";

    /** Displayed text for empty slots. */
    private static final String EMPTY_SLOT_TEXT = "-";

    /** Text for the try again button. */
    private static final String TRY_AGAIN_TEXT = "Try Again";

    /** Text for the quit button. */
    private static final String QUIT_TEXT = "Quit";

    /** Spacing between VBox elements. */
    private static final int VBOX_SPACING = 10;

    /** Index for the first element. */
    private static final int FIRST_INDEX = 0;

    /** Gap size horizontally between grid items.*/
    private static final int GRID_HGAP = 10;

    /** Gap size vertically between grid items.*/
    private static final int GRID_VGAP = 10;

    /** Padding between grid items. */
    private static final int GRID_PADDING = 15;

    /* Tracks if JavaFX has been started already. */
    private static boolean javafxStarted = false;

    /* Displays the current number to place. */
    private Label currentNumberLabel;

    /* Primary game stage. */
    private Stage stage;

    /* NumberGame logic object. */
    private final NumberGame game;

    /* Button array representing the grid. */
    private final Button[] buttons;

    /**
     * Constructs a NumberGameFx.
     */
    public NumberGameFx()
    {
        game = new NumberGame();
        buttons = new Button[GRID_SIZE];
    }

    /**
     * Entry point into the NumberGame GUI.
     * Starts JavaFX once, then launches stages using runLater.
     */
    public static void numberGame()
    {
        if (!javafxStarted)
        {
            Platform.setImplicitExit(false);
            javafxStarted = true;
            Platform.startup(NumberGameFx::launchGame);
        }
        else
        {
            Platform.runLater(NumberGameFx::launchGame);
        }
    }

    /**
     * Starts the JavaFX stage.
     *
     * @param stage the primary stage
     */
    public void startGame(final Stage stage)
    {
        final VBox root;
        final GridPane grid;
        final Scene scene;

        this.stage = stage;

        currentNumberLabel = new Label();

        grid = new GridPane();
        for (int i = FIRST_INDEX; i < COLS; i++)
        {
            final ColumnConstraints column;
            column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            column.setFillWidth(true);
            grid.getColumnConstraints().add(column);
        }

        for (int i = FIRST_INDEX; i < ROWS; i++)
        {
            final RowConstraints row;
            row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setFillHeight(true);
            grid.getRowConstraints().add(row);
        }

        grid.setHgap(GRID_HGAP);
        grid.setVgap(GRID_VGAP);
        grid.setStyle("-fx-padding: " + GRID_PADDING + "px;");
        VBox.setVgrow(grid, Priority.ALWAYS);
        buildGrid(grid);

        root = new VBox(VBOX_SPACING);
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

    /*
     * Launches the NumberGameFx GUI in a new Stage.
     */
    private static void launchGame()
    {
        final NumberGameFx fx;
        final Stage stage;

        fx = new NumberGameFx();
        stage = new Stage();

        fx.startGame(stage);
    }

    /*
     * Builds the grid of buttons for the game.
     */
    private void buildGrid(final GridPane grid)
    {
        int index;
        index = FIRST_INDEX;

        for (int row = FIRST_INDEX; row < ROWS; row++)
        {
            for (int col = FIRST_INDEX; col < COLS; col++)
            {
                final int buttonIndex;
                final Button button;

                buttonIndex = index;

                button = new Button(EMPTY_SLOT_TEXT);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);

                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                button.setOnAction(event -> handleButtonClick(buttonIndex));

                buttons[index] = button;
                grid.add(button, col, row);

                index++;
            }
        }
    }

    /*
     * Starts a new round by resetting the game and refreshing the grid.
     */
    private void startNewRound()
    {
        int i;

        game.play();

        for (i = FIRST_INDEX; i < GRID_SIZE; i++)
        {
            buttons[i].setText(EMPTY_SLOT_TEXT);
            buttons[i].setDisable(false);
        }

        updateCurrentNumberLabel();
        refreshButtonStates();
    }

    /*
     * Updates the label that displays the current number.
     */
    private void updateCurrentNumberLabel()
    {
        currentNumberLabel.setText("Current number: " + game.getCurrentNumber());
    }

    /*
     * Enables or disables buttons depending on whether the placement is legal.
     */
    private void refreshButtonStates()
    {
        int i;

        for (i = FIRST_INDEX; i < GRID_SIZE; i++)
        {
            if (game.getPlacedNumbers()[i] == null)
            {
                buttons[i].setDisable(!game.isLegalPlacement(i, game.getCurrentNumber()));
            }
        }
    }

    /*
     * Displays the end-of-game dialog and asks the user to retry or quit.
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

    /*
     * Builds the score summary text for the popup dialogs.
     */
    private String buildScoreText()
    {
        return game.buildScoreText();
    }

    /*
     * Handles button clicks and processes placements.
     */
    private void handleButtonClick(final int index)
    {
        final boolean success;

        success = game.placeNumber(index);

        if (!success)
        {
            return;
        }

        buttons[index].setText(String.valueOf(game.getPlacedNumbers()[index]));
        buttons[index].setDisable(true);

        if (game.isGameWon())
        {
            showEndDialog(true);
            return;
        }

        game.generateNextNumber();
        updateCurrentNumberLabel();
        refreshButtonStates();

        if (!game.hasLegalMove())
        {
            game.stopPlaying();
            showEndDialog(false);
        }
    }

    /*
     * Ends the game session, shows final score, and closes the stage.
     */
    private void exitProcedure()
    {
        final Alert alert;

        game.stopPlaying();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Score");
        alert.setHeaderText("NumberGame Summary");
        alert.setContentText(buildScoreText());
        alert.showAndWait();

        stage.close();
    }
}