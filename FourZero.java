import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.Scanner;
import java.io.FileWriter;
/**
 * This class is responsible for starting the game.
 * @author Hamish Tartaglia, Richard James, Vlad Stejeroiu,
 * Mindaugas Kavaliauskas and William Playle-de Vries
 *
 */
public class FourZero extends Application {
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 448;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 448;
    private static final int CANVAS_HEIGHT = 448;

    //This is what the game is drawn onto
    private Canvas canvas;
    //This is the stage that everything is displayed
    static Stage curStage;
    //These show the players inventory as images
    private Label redAxeNum;
    private Label purpleAxeNum;
    private Label blueAxeNum;
    private Label dynamiteNum;
    private Label lavaCharm;
    private Label flippers;
    //These are used to calculate and display the time
    private Label userTime = new Label();
    private long scoreTest = 10000;
    private long pauseTime;
    private long normalTime;
    //The world that is being created
    private static World level;
    //Used for the music
    private MediaPlayer mediaPlayer;
    //Used to build the game
    private Pane pause = buildPauseUI();
    //Creates a new scene for the pause menu
    private Scene pauseScene = new Scene(pause, WINDOW_WIDTH, WINDOW_HEIGHT);
    // X and Y coordinate of player
    private Player player;

    // Build the GUI
    private Pane root = buildGUI();
    private BorderPane border = new BorderPane();

    //Creates a scene for the game
    private Scene newScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);
    //Create a scene from the GUI
    private Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    //Used to calculate the time played
    long startTime = System.currentTimeMillis();
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            normalTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = normalTime / 1000;
            long secondsDisplay = elapsedSeconds % 60;
            long minutesDisplay = elapsedSeconds / 60;
            userTime.setText("Timer: \n" + minutesDisplay + " : " + secondsDisplay);
        }
    };

    /**
     * Starts the game if it is loading a save.
     * @param pS primaryStage
     * @param filename username of file to be loaded
     * @throws FileNotFoundException File not found
     */
    public void startGame(Stage pS, String filename) throws FileNotFoundException {
        level = new World();
        level.make(filename);
        player = level.getPlayer();
        player.setUsername(filename);
        start(pS);
    }

    /**
     * Starts the game if it has to load a fresh level (overwrite save).
     * @param pS primaryStage
     * @param levelNum level to be loaded
     * @param username username
     * @throws FileNotFoundException file not found
     */
    public void startGame(Stage pS, int levelNum, String username) throws FileNotFoundException {
        Scanner in ;
        FileWriter fw;
        try { in = new Scanner(new File(levelNum + "fresh.txt"));
            fw = new FileWriter(username + ".txt", false);
            fw.write( in .nextLine() + username + "," + levelNum + ",10000,0\n");
            while ( in .hasNextLine()) {
                fw.write( in .nextLine() + "\n");
            } in .close();
            fw.close();
            level = new World();
            level.make(username);
            player = level.getPlayer();
            start(pS);
        } catch (Exception f) {
            System.out.println("Can't write.");
        }
    }

    /**
     * This starts the game
     */
    public void start(Stage primaryStage) {
    	//Starts the timer
        timer.start();
        //Creates a BorderPane for the game and adds the canvas and the images for the player inventory
        border = new BorderPane();
        border.setLeft(canvas);
        border.setCenter(addGridPane());
        border.getStylesheets().add("style.css");
        //Creates a new scene using the borderpane
        Scene newScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);
        //Register an event handler for key presses
        newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
                processKeyEvent(event);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
        });
        pauseScene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEventPause);
        drawGame();
        curStage = primaryStage;
        //Changes the scene to the game interface
        curStage.setScene(newScene);
        //Allows music to be played
        setMusic();
        mediaPlayer.play();
        //Shows the game
        curStage.show();
    }

    /**
     * Creates a GridPane to add to the game interface.
     * @return gridPane this is the GridPane thats added
     */
    private GridPane addGridPane() {
    	//Assigning images for the players inventory
        Image redAxe = new Image(getClass().getResourceAsStream("assets/png/red_axe.png"), 50, 50, true, false);
        Image purpleAxe = new Image(getClass().getResourceAsStream("assets/png/purple_axe.png"), 50, 50, true, false);
        Image blueAxe = new Image(getClass().getResourceAsStream("assets/png/blue_axe.png"), 50, 50, true, false);
        Image dynamite = new Image(getClass().getResourceAsStream("assets/png/dynamite.png"), 50, 50, true, false);
        Image count0 = new Image(getClass().getResourceAsStream("assets/png/x0.png"), 50, 50, true, false);
        //Flippers and lava charm are greyed out if not collected yet
        Image dullFlippers = new Image(getClass().getResourceAsStream("assets/png/flippers_shadow.png"), 50, 50, true,
            false);
        Image dullCharm = new Image(getClass().getResourceAsStream("assets/png/lava_charm_shadow.png"), 50, 50, true,
            false);
        //Creates a pause button for the game interface
        Button pauseButton = new Button("Pause");
        pauseButton.setId("pauseButton");
        //Adds the images of the players inventory to labels
        Label redAxeLabel = new Label();
        redAxeLabel.setGraphic(new ImageView(redAxe));
        redAxeLabel.setScaleX(1.5);
        redAxeLabel.setScaleY(1.5);
        Label purpleAxeLabel = new Label();
        purpleAxeLabel.setGraphic(new ImageView(purpleAxe));
        purpleAxeLabel.setScaleX(1.5);
        purpleAxeLabel.setScaleY(1.5);
        Label blueAxeLabel = new Label();
        blueAxeLabel.setGraphic(new ImageView(blueAxe));
        blueAxeLabel.setScaleX(1.5);
        blueAxeLabel.setScaleY(1.5);
        Label dynamiteLabel = new Label();
        dynamiteLabel.setGraphic(new ImageView(dynamite));
        dynamiteLabel.setScaleX(1.5);
        dynamiteLabel.setScaleY(1.5);
        //These set the count of each item to an image of 'x0'
        redAxeNum = new Label();
        redAxeNum.setGraphic(new ImageView(count0));
        purpleAxeNum = new Label();
        purpleAxeNum.setGraphic(new ImageView(count0));
        blueAxeNum = new Label();
        blueAxeNum.setGraphic(new ImageView(count0));
        dynamiteNum = new Label();
        dynamiteNum.setGraphic(new ImageView(count0));
        flippers = new Label();
        flippers.setGraphic(new ImageView(dullFlippers));
        lavaCharm = new Label();
        lavaCharm.setGraphic(new ImageView(dullCharm));
        //Adds labels for timer and music info in the game interface
        Label musicLabel = new Label("Press 'M' for music");
        musicLabel.setId("timer");
        musicLabel.setWrapText(true);
        userTime.setId("timer");
        updateItems();
        //Creates the GridPane for the game interface
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        gridPane.getStylesheets().add("style.css");
        gridPane.setId("grid");
        //Adds all images, label and the button to the game interface GridPane
        gridPane.add(pauseButton, 1, 0, 2, 2);
        gridPane.add(redAxeLabel, 1, 2);
        gridPane.add(purpleAxeLabel, 1, 4);
        gridPane.add(blueAxeLabel, 1, 6);
        gridPane.add(dynamiteLabel, 1, 8);
        gridPane.add(lavaCharm, 1, 10);
        gridPane.add(redAxeNum, 2, 2);
        gridPane.add(purpleAxeNum, 2, 4);
        gridPane.add(blueAxeNum, 2, 6);
        gridPane.add(dynamiteNum, 2, 8);
        gridPane.add(flippers, 2, 10);
        gridPane.add(userTime, 1, 11);
        gridPane.add(musicLabel, 2, 11);
        //When the pause button is pressed, it stops the user input
        //It launches the pause menu interface
        pauseButton.setOnAction(e -> {
            pauseTime = System.currentTimeMillis() - startTime;
            pause = buildPauseUI();
            pauseScene = new Scene(pause, WINDOW_WIDTH, WINDOW_HEIGHT);
            pauseScene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEventPause);
            curStage.setScene(pauseScene);
        });
        return gridPane;
    }

    /**
     * Process a key event due to a key being pressed, e.g., to the player.
     * @param event The key event that was pressed.
     */
    public void processKeyEvent(KeyEvent event) throws FileNotFoundException {
        {
            switch (event.getCode()) {

                case RIGHT:
                    // Right key was pressed. So move the player right by one cell. Update inventory images
                    if (player.validMove(level.getCell(player.getX() + 1, player.getY()), level, 'e')) {
                        player.setTime(normalTime);
                        player.moveRight();
                        level.updateWorld(player);
                        updateItems();
                    }
                    break;
                case LEFT:
                	// Left key was pressed. So move the player right by one cell. Update inventory images
                    if (player.validMove(level.getCell(player.getX() - 1, player.getY()), level, 'w')) {
                        player.setTime(normalTime);
                        player.moveLeft();
                        level.updateWorld(player);
                        updateItems();
                    }
                    break;
                case UP:
                	// Up key was pressed. So move the player right by one cell. Update inventory images
                    if (player.validMove(level.getCell(player.getX(), player.getY() - 1), level, 'n')) {
                        player.setTime(normalTime);
                        player.moveUp();
                        level.updateWorld(player);
                        updateItems();
                    }
                    break;
                case DOWN:
                	// Down key was pressed. So move the player right by one cell. Update inventory images
                    if (player.validMove(level.getCell(player.getX(), player.getY() + 1), level, 's')) {
                        player.setTime(normalTime);
                        player.moveDown();
                        level.updateWorld(player);
                        updateItems();
                    }
                    break;
                case M:
                	//Turns the music on/off when 'm' is pressed
                    musicSwitch();
                    break;
                default:
                    // Do nothing
                    break;
            }
            // Redraw game as the player may have moved.
            drawGame();
            // Consume the event. This means we mark it as dealt with. This stops other GUI
            // nodes (buttons etc) responding to it.
            event.consume();
        }
    }
    /**
     * Updates the images of the players inventory on the side of the game interface.
     */
    private void updateItems() {
        redAxeNum.setGraphic(new ImageView(new Image(
            getClass().getResourceAsStream(
                "assets/png/x" + Integer.toString(player.quantityColourAxe("red")) + ".png"),
            50, 50, true, false)));
        purpleAxeNum.setGraphic(new ImageView(new Image(
            getClass().getResourceAsStream(
                "assets/png/x" + Integer.toString(player.quantityColourAxe("purple")) + ".png"),
            50, 50, true, false)));
        blueAxeNum.setGraphic(new ImageView(new Image(
            getClass().getResourceAsStream(
                "assets/png/x" + Integer.toString(player.quantityColourAxe("blue")) + ".png"),
            50, 50, true, false)));
        dynamiteNum.setGraphic(new ImageView(new Image(
            getClass().getResourceAsStream("assets/png/x" + Integer.toString(player.dynamiteNum()) + ".png"), 50,
            50, true, false)));
        if (player.hasLavaCharm()) {
            lavaCharm.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("assets/png/lava_charm.png"), 50, 50, true, false)));
        } else {
            lavaCharm.setGraphic(new ImageView(new Image(
                getClass().getResourceAsStream("assets/png/lava_charm_shadow.png"), 50, 50, true, false)));
        }
        if (player.hasFlippers()) {
            flippers.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("assets/png/flippers.png"), 50, 50, true, false)));
        } else {
            flippers.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("assets/png/flippers_shadow.png"), 50, 50, true, false)));
        }
    }
    
    /**
     * This takes the user back to the main menu.
     */
    public void backToMenu() {
        Stage s = new Stage();
        try {
            new MainMenu().start(s);
            curStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Checks for activity.
     * @param event
     */
    public void processKeyEventPause(KeyEvent event) {
        switch (event.getCode()) {
            case M:
                musicSwitch();
                break;
            default:
                break;
        }
    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw world
        level.draw(gc, player.getX() - 3, player.getX() + 3, player.getY() - 3, player.getY() + 3);

        // Draw player at current location
        player.draw(gc);
    }

    /**
     * Restart the game.
     */
    public void restartGame() {
        scoreTest -= 250;
        // We just move the player to cell (1, 1)
        try {
            player.restartGame(level);
            drawGame();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot load file.");
        }
    }
    
    /**
     * Creates the pause game interface
     * @return pauseMenu this is the Pane that builds the pause menu
     */
    private Pane buildPauseUI() {
    	//Setting an image as the boulder image
        Image boulderImage = new Image(getClass().getResourceAsStream("assets/png/boulder.png"), 50, 50, true, false);
        //Creating some blank labels that will change to the boulder when the user hovers over a button
        Label boulder1 = new Label();
        boulder1.setScaleX(0.5);
        boulder1.setScaleY(0.5);
        Label boulder2 = new Label();
        boulder2.setScaleX(0.5);
        boulder2.setScaleY(0.5);
        Label boulder3 = new Label();
        boulder3.setScaleX(0.5);
        boulder3.setScaleY(0.5);
        Label boulder4 = new Label();
        boulder4.setScaleX(0.5);
        boulder4.setScaleY(0.5);
        Label boulder5 = new Label();
        boulder5.setScaleX(0.5);
        boulder5.setScaleY(0.5);
        //Creating the titles and buttons for the pause menu
        Label pauseLabel = new Label("Game Paused!");
        pauseLabel.setId("titles");
        Button saveButton = new Button("Save Game", boulder1);
        Button backButton = new Button("Back to Menu", boulder2);
        Button quitButton = new Button("Quit Game", boulder3);
        Button resumeButton = new Button("Resume Game", boulder4);
        Button restartButton = new Button("Restart Level", boulder5);
        //Creaing a GridPane
        GridPane pauseGrid = new GridPane();
        pauseGrid.setPadding(new Insets(3));
        pauseGrid.setVgap(20);
        pauseGrid.setHgap(20);
        //Adding elements to the GridPane
        pauseGrid.add(resumeButton, 0, 1);
        pauseGrid.add(saveButton, 1, 1);
        pauseGrid.add(backButton, 0, 2);
        pauseGrid.add(restartButton, 1, 2);
        pauseGrid.add(quitButton, 0, 3, 2, 1);
        pauseGrid.setAlignment(javafx.geometry.Pos.CENTER);
        pauseGrid.getStylesheets().add("style.css");
        //Creating a BorderPane for the pause menu and adding elements
        BorderPane pauseMenu = new BorderPane();
        pauseMenu.setTop(pauseLabel);
        pauseMenu.setCenter(pauseGrid);
        pauseMenu.setAlignment(pauseLabel, Pos.CENTER);
        pauseMenu.getStylesheets().add("style.css");

        //When the user hovers the mouse the labels will change to the boulder
        saveButton.setOnMouseEntered(e -> boulder1.setGraphic(new ImageView(boulderImage)));
        saveButton.setOnMouseExited(e -> boulder1.setGraphic(null));
        backButton.setOnMouseEntered(e -> boulder2.setGraphic(new ImageView(boulderImage)));
        backButton.setOnMouseExited(e -> boulder2.setGraphic(null));
        quitButton.setOnMouseEntered(e -> boulder3.setGraphic(new ImageView(boulderImage)));
        quitButton.setOnMouseExited(e -> boulder3.setGraphic(null));
        resumeButton.setOnMouseEntered(e -> boulder4.setGraphic(new ImageView(boulderImage)));
        resumeButton.setOnMouseExited(e -> boulder4.setGraphic(null));
        restartButton.setOnMouseEntered(e -> boulder5.setGraphic(new ImageView(boulderImage)));
        restartButton.setOnMouseExited(e -> boulder5.setGraphic(null));
        //Setting actions for the buttons either exits or takes back to main menu
        quitButton.setOnAction(e -> System.exit(0));
        backButton.setOnAction(e -> backToMenu());
        //Writes a save to the file when pressed
        saveButton.setOnAction(e -> {
            try {
                level.writeSave(player);
            } catch (IOException f) {
                System.out.println("Save not worked.");
            }
        });
        //Restarts the level when pressed
        restartButton.setOnAction(e -> {
            restartGame();
            try {
                level.make(player.getUsername());
            } catch (FileNotFoundException ezop) {
                System.out.println("File not found.");
            }
            resumeGame();
        });
        //Resumes the game when pressed
        resumeButton.setOnAction(e -> {
            resumeGame();
        });
        return pauseMenu;
    }

    /**
     * Resumes the game when pressed
     */
    public void resumeGame() {
    	//Creates a borderPane and adds the canvas and the player inventory images
        BorderPane border = new BorderPane();
        border.setLeft(canvas);
        border.setCenter(addGridPane());
        border.getStylesheets().add("style.css");
        //Allows keys to be pressed
        newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
                processKeyEvent(event);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
        });
        //Sets the scene and changes it to the game interface
        Scene newScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);
        curStage.setScene(newScene);
    }
    
    /**
     * Set music.
     */
    public void setMusic() {
        String musicFile = "assets/sample1.wav";
        Media sound = new Media((new File(musicFile)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    /**
     * Switch music.
     */
    public void musicSwitch() {

        if (mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
        } else {
            mediaPlayer.setMute(true);
        }
    }

    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
    private Pane buildGUI() {
        // Create top-level panel that will hold all GUI
        BorderPane root = new BorderPane();
        // Create canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);
        // Create a toolbar with some nice padding and spacing
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(toolbar);
        // Create toolbar content
        Button restartButton = new Button("Restart");
        toolbar.getChildren().add(restartButton);

        // Add button event handlers
        restartButton.setOnAction(e -> {
            restartGame();
        });

        return root;
    }
}