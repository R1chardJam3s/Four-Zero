import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class is used to load the main menu, from this you can play the game.
 * @author Hamish Tartaglia
 */

public class MainMenu extends Application {
    // The dimensions of the window.
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 448;

    // Used to scale the size of images by half.
    private static final double SCALE_BY_HALF = 0.5;

    // Used to set the veritcal and horizontal gap for GridPanes.
    private static final int V_AND_H_GAP = 20;

    // Used to set the padding for GridPanes.
    private static final int PADDING = 3;

    /**
     * This method creates the main menu.
     * @param primaryStage the stage the GUI will use
     */
    public void start(Stage primaryStage) throws Exception {
        // Some scenes that will be used in the Main Menu GUI.
        Scene mainMenuScene;
        Scene profileSelection;
        Scene createScene;
        Scene leaderboard1;
        Scene leaderboard2;
        Scene leaderboard3;
        Scene leaderboardSelect;
        // Creating image variables used in the GUI.
        Image boulder = new Image(getClass().getResourceAsStream("assets/png/boulder.png"), 50, 50, true, false);
        Image fourZero = new Image(getClass().getResourceAsStream("assets/png/FourZero.png"));

        // Used for buttons - when the user hovers over a button, the lable will change to a boulder.
        Label boulder1 = new Label();
        boulder1.setScaleX(SCALE_BY_HALF);
        boulder1.setScaleY(SCALE_BY_HALF);
        Label boulder2 = new Label();
        boulder2.setScaleX(SCALE_BY_HALF);
        boulder2.setScaleY(SCALE_BY_HALF);
        Label boulder3 = new Label();
        boulder3.setScaleX(SCALE_BY_HALF);
        boulder3.setScaleY(SCALE_BY_HALF);
        Label boulder4 = new Label();
        boulder4.setScaleX(SCALE_BY_HALF);
        boulder4.setScaleY(SCALE_BY_HALF);
        Label boulder5 = new Label();
        boulder5.setScaleX(SCALE_BY_HALF);
        boulder5.setScaleY(SCALE_BY_HALF);
        Label boulder6 = new Label();
        boulder6.setScaleX(SCALE_BY_HALF);
        boulder6.setScaleY(SCALE_BY_HALF);
        Label boulder7 = new Label();
        boulder7.setScaleX(SCALE_BY_HALF);
        boulder7.setScaleY(SCALE_BY_HALF);
        Label boulder8 = new Label();
        boulder8.setScaleX(SCALE_BY_HALF);
        boulder8.setScaleY(SCALE_BY_HALF);
        Label boulder9 = new Label();
        boulder9.setScaleX(SCALE_BY_HALF);
        boulder9.setScaleY(SCALE_BY_HALF);
        Label boulder11 = new Label();
        boulder11.setScaleX(SCALE_BY_HALF);
        boulder11.setScaleY(SCALE_BY_HALF);
        Label boulder12 = new Label();
        boulder12.setScaleX(SCALE_BY_HALF);
        boulder12.setScaleY(SCALE_BY_HALF);
        Label boulder13 = new Label();
        boulder13.setScaleX(SCALE_BY_HALF);
        boulder13.setScaleY(SCALE_BY_HALF);
        Label boulder14 = new Label();
        boulder14.setScaleX(SCALE_BY_HALF);
        boulder14.setScaleY(SCALE_BY_HALF);
        // Used for the main title in the main menu scene.
        Label title = new Label();
        title.setGraphic(new ImageView(fourZero));
        //Titles used in each scene in the main menu
        Label profileLabel = new Label("Select Profile and Level!");
        profileLabel.setId("titles");
        Label createLabel = new Label("Create Profile and Play!");
        createLabel.setId("titles");
        // Message of the day labels used in each scene.
        Label motdLabel1 = new Label(Motd.getMotd());
        motdLabel1.setId("motd");
        motdLabel1.setWrapText(true);
        Label motdLabel2 = new Label(Motd.getMotd());
        motdLabel2.setId("motd");
        motdLabel2.setWrapText(true);
        Label motdLabel3 = new Label(Motd.getMotd());
        motdLabel3.setId("motd");
        motdLabel3.setWrapText(true);
        Label motdLabel4 = new Label(Motd.getMotd());
        motdLabel4.setId("motd");
        motdLabel4.setWrapText(true);

        // The buttons used in each scene.
        Button newGameButton = new Button("New Game", boulder1);
        Button loadGameButton = new Button("Load Game", boulder2);
        Button loadNewGame = new Button("Play", boulder3);
        Button leaderboardButton = new Button("Leaderboards", boulder4);
        Button exitButton = new Button("Exit", boulder5);
        Button createProfileButton = new Button("Create Profile", boulder6);
        Button backButton1 = new Button("Back", boulder7);
        Button backButton2 = new Button("Back", boulder8);
        Button loadSavedGame = new Button("Select Level", boulder9);
        Button lvl1BackButton = new Button("Back");
        Button lvl2BackButton = new Button("Back");
        Button lvl3BackButton = new Button("Back");

        //TextField for when the user adds a profile.
        TextField userNameBox = new TextField("User Name");

        // Main menu grid pane - set up.
        GridPane mainMenuPane = new GridPane();
        mainMenuPane.setVgap(V_AND_H_GAP);
        mainMenuPane.setHgap(V_AND_H_GAP);
        mainMenuPane.setAlignment(javafx.geometry.Pos.CENTER);
        mainMenuPane.getStylesheets().add("style.css");
        // Main menu grid pane - adding buttons.
        mainMenuPane.add(newGameButton, 0, 1);
        mainMenuPane.add(loadGameButton, 1, 1);
        mainMenuPane.add(leaderboardButton, 0, 2);
        mainMenuPane.add(exitButton, 1, 2);

        // Main menu BorderPane.
        BorderPane mainMenu = new BorderPane();
        mainMenu.setTop(title);
        mainMenu.setCenter(mainMenuPane);
        mainMenu.setBottom(motdLabel1);
        mainMenu.setAlignment(title, Pos.CENTER);
        mainMenu.setAlignment(motdLabel1, Pos.CENTER);
        mainMenuScene = new Scene(mainMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
        mainMenuScene.getStylesheets().add("style.css");

        // Profile creation grid pane - set up.
        GridPane createProfile = new GridPane();
        createProfile.setPadding(new Insets(PADDING));
        createProfile.setVgap(V_AND_H_GAP);
        createProfile.setHgap(V_AND_H_GAP);
        //  Profile creation grid pane - adding elements to it.
        createProfile.add(userNameBox, 0, 1, 2, 1);
        createProfile.add(loadNewGame, 0, 2);
        createProfile.add(backButton2, 1, 2);
        createProfile.setAlignment(javafx.geometry.Pos.CENTER);
        createProfile.getStylesheets().add("style.css");

        // Create profile BorderPane.
        BorderPane createProf = new BorderPane();
        createProf.setTop(createLabel);
        createProf.setCenter(createProfile);
        createProf.setBottom(motdLabel2);
        createProf.setAlignment(createLabel, Pos.CENTER);
        createProf.setAlignment(motdLabel2, Pos.CENTER);
        createScene = new Scene(createProf, WINDOW_WIDTH, WINDOW_HEIGHT);
        createScene.getStylesheets().add("style.css");

        // Drop down list of profiles using the file of profiles.
        ObservableList <Profile> profileOptions =
            FXCollections.observableArrayList(Profile.getProfiles());
        ComboBox <Profile> profileComboBox = new ComboBox <Profile> ();
        profileComboBox.setItems(profileOptions);
        profileComboBox.setPromptText("Profile Select");

        // Drop down list of levels the selected profiles can use.
        ObservableList <Integer> levelOptions =
            FXCollections.observableArrayList();
        ComboBox <Integer> levelComboBox = new ComboBox <Integer> ();
        levelComboBox.setItems(levelOptions);
        levelComboBox.setPromptText("Level Select");

        // Profile select GridPane - set up.
        GridPane profileSelect = new GridPane();
        profileSelect.setPadding(new Insets(PADDING));
        profileSelect.setVgap(V_AND_H_GAP);
        profileSelect.setHgap(V_AND_H_GAP);
        // Profile select GridPane - adding elements.
        profileSelect.add(profileComboBox, 0, 1);
        profileSelect.add(levelComboBox, 1, 1);
        profileSelect.add(loadSavedGame, 0, 2);
        profileSelect.add(backButton1, 1, 2);
        profileSelect.setAlignment(javafx.geometry.Pos.CENTER);
        profileSelect.getStylesheets().add("style.css");

        // Select profile BorderPane.
        BorderPane selectProf = new BorderPane();
        selectProf.setTop(profileLabel);
        selectProf.setCenter(profileSelect);
        selectProf.setBottom(motdLabel3);
        selectProf.setAlignment(profileLabel, Pos.CENTER);
        selectProf.setAlignment(motdLabel3, Pos.CENTER);
        profileSelection = new Scene(selectProf, WINDOW_WIDTH, WINDOW_HEIGHT);
        profileSelection.getStylesheets().add("style.css");

        // Setting the on actions for buttons - taking to another scene.
        loadGameButton.setOnAction(e -> primaryStage.setScene(profileSelection));
        newGameButton.setOnAction(e -> primaryStage.setScene(createScene));
        backButton1.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        backButton2.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        // When user selects a profile this adds their available levels to the drop down list.
        profileComboBox.setOnAction(e -> {
            levelOptions.clear();
            Profile help = profileComboBox.getSelectionModel().getSelectedItem();
            for (int i = 1; i <= help.getLevels().size(); i++) {
                levelOptions.add(i);
            }
        });
        // Starts the level the user selected.
        loadSavedGame.setOnAction(e -> {
            Profile profileSelected = profileComboBox.getSelectionModel().getSelectedItem();
            Integer levelSelected = levelComboBox.getSelectionModel().getSelectedItem();
            FourZero startLevel = new FourZero();
            if (levelSelected != null) {
                try {
                    Scanner in = new Scanner(new File(profileSelected.getUserName() + ".txt"));
                    String cl = in .nextLine(); in .close();
                    String[] curL = cl.split(",");
                    int curLevel = Integer.parseInt(curL[5]);
                    if (curLevel == levelSelected) {
                        startLevel.startGame(primaryStage, profileSelected.getUserName());
                    } else {
                        startLevel.startGame(primaryStage, levelSelected, profileSelected.getUserName());
                    }
                } catch (FileNotFoundException f) {
                    System.out.println("No file can be loaded.");
                }
            }
        });
        // Starts a new game for the profile and writes them to the file of profiles.
        loadNewGame.setOnAction(e -> {
            String profileName = userNameBox.getText();
            Profile profileAdd = new Profile(profileName);
            try {
                Profile.addProfile(profileAdd);
            } catch (IOException e1) {
                System.out.print("Cant add Profile");
            }
            profileOptions.add(profileAdd);
            profileComboBox.setItems(profileOptions);
            Scanner in ;
            FileWriter fw;
            try { in = new Scanner(new File("1fresh.txt"));
                fw = new FileWriter(profileName + ".txt", false);
                fw.write( in .nextLine() + profileName + ",false,false,1,10000,0\n");
                while ( in .hasNextLine()) {
                    fw.write( in .nextLine() + "\n");
                } in .close();
                fw.close();
                FourZero startLevel = new FourZero();
                startLevel.startGame(primaryStage, profileName);
            } catch (Exception f) {
                System.out.println("Can't write.");
            }
        });
        // Closes the GUI.
        exitButton.setOnAction(e -> primaryStage.close());
        // When the user hovers over a button, adds a boulder to the button
        // used for aesthetics.
        newGameButton.setOnMouseEntered(e -> boulder1.setGraphic(new ImageView(boulder)));
        newGameButton.setOnMouseExited(e -> boulder1.setGraphic(null));
        loadGameButton.setOnMouseEntered(e -> boulder2.setGraphic(new ImageView(boulder)));
        loadGameButton.setOnMouseExited(e -> boulder2.setGraphic(null));
        loadNewGame.setOnMouseEntered(e -> boulder3.setGraphic(new ImageView(boulder)));
        loadNewGame.setOnMouseExited(e -> boulder3.setGraphic(null));
        leaderboardButton.setOnMouseEntered(e -> boulder4.setGraphic(new ImageView(boulder)));
        leaderboardButton.setOnMouseExited(e -> boulder4.setGraphic(null));
        exitButton.setOnMouseEntered(e -> boulder5.setGraphic(new ImageView(boulder)));
        exitButton.setOnMouseExited(e -> boulder5.setGraphic(null));
        createProfileButton.setOnMouseEntered(e -> boulder6.setGraphic(new ImageView(boulder)));
        createProfileButton.setOnMouseExited(e -> boulder6.setGraphic(null));
        backButton1.setOnMouseEntered(e -> boulder7.setGraphic(new ImageView(boulder)));
        backButton1.setOnMouseExited(e -> boulder7.setGraphic(null));
        backButton2.setOnMouseEntered(e -> boulder8.setGraphic(new ImageView(boulder)));
        backButton2.setOnMouseExited(e -> boulder8.setGraphic(null));
        loadSavedGame.setOnMouseEntered(e -> boulder9.setGraphic(new ImageView(boulder)));
        loadSavedGame.setOnMouseExited(e -> boulder9.setGraphic(null));

        // Creates new leader board for each level.
        Leaderboard level1Leaderboard = new Leaderboard();
        Leaderboard level2Leaderboard = new Leaderboard();
        Leaderboard level3Leaderboard = new Leaderboard();
        // Title and buttons for the Leaderboard scene.
        Label leaderLabel = new Label("Leaderboards!");
        leaderLabel.setId("titles");
        Button lvl1Button = new Button("Level 1", boulder11);
        Button lvl2Button = new Button("Level 2", boulder12);
        Button lvl3Button = new Button("Level 3", boulder13);
        Button backButton4 = new Button("Back", boulder14);
        // Leaderboard selection GridPane - set up.
        GridPane leaderLevelsGrid = new GridPane();
        leaderLevelsGrid.setPadding(new Insets(PADDING));
        leaderLevelsGrid.setVgap(V_AND_H_GAP);
        leaderLevelsGrid.setHgap(V_AND_H_GAP);
        // Leaderboard selection GridPane - adding elements.
        leaderLevelsGrid.add(lvl1Button, 0, 1);
        leaderLevelsGrid.add(lvl2Button, 1, 1);
        leaderLevelsGrid.add(lvl3Button, 0, 2);
        leaderLevelsGrid.add(backButton4, 1, 2);
        leaderLevelsGrid.setAlignment(javafx.geometry.Pos.CENTER);
        leaderLevelsGrid.getStylesheets().add("style.css");

        // Leaderboard BorderPane.
        BorderPane leaderLeavelsBoarder = new BorderPane();
        leaderLeavelsBoarder.setTop(leaderLabel);
        leaderLeavelsBoarder.setCenter(leaderLevelsGrid);
        leaderLeavelsBoarder.setBottom(motdLabel4);
        leaderLeavelsBoarder.setAlignment(leaderLabel, Pos.CENTER);
        leaderLeavelsBoarder.setAlignment(motdLabel4, Pos.CENTER);
        leaderboardSelect = new Scene(leaderLeavelsBoarder, WINDOW_WIDTH, WINDOW_HEIGHT);
        leaderboardSelect.getStylesheets().add("style.css");
        // Loads the leader boards for each level.
        level1Leaderboard.loadScore(1);
        level2Leaderboard.loadScore(2);
        level3Leaderboard.loadScore(3);
        // Titles and column headings for level 1 leaderboard.
        Label leaderlbl1 = new Label("Level 1 Leaderboard!");
        leaderlbl1.setId("titles");
        Label leaderUserlbl1 = new Label("Username");
        leaderUserlbl1.setId("titles");
        Label leaderScorelbl1 = new Label("Score");
        leaderScorelbl1.setId("titles");
        // Adds the top three names and scores into the level 1 leader board labels.
        Label lvl1scorelbl1 = new Label(Integer.toString(level1Leaderboard.getFileScore0()));
        lvl1scorelbl1.setId("titles");
        Label lvl1namelbl1 = new Label(level1Leaderboard.getFileName0());
        lvl1namelbl1.setId("titles");
        Label lvl1scorelbl2 = new Label(Integer.toString(level1Leaderboard.getFileScore1()));
        lvl1scorelbl2.setId("titles");
        Label lvl1namelbl2 = new Label(level1Leaderboard.getFileName1());
        lvl1namelbl2.setId("titles");
        Label lvl1scorelbl3 = new Label(Integer.toString(level1Leaderboard.getFileScore2()));
        lvl1scorelbl3.setId("titles");
        Label lvl1namelbl3 = new Label(level1Leaderboard.getFileName2());
        lvl1namelbl3.setId("titles");
        // Level 1 leader board GridPane - set up.
        GridPane leaderGrid1 = new GridPane();
        leaderGrid1.setPadding(new Insets(PADDING));
        leaderGrid1.setVgap(V_AND_H_GAP);
        leaderGrid1.setHgap(V_AND_H_GAP);
        // Level 1 leader board GridPane - adds the username and scores to gridpane.
        leaderGrid1.add(leaderScorelbl1, 1, 1);
        leaderGrid1.add(lvl1scorelbl1, 1, 2);
        leaderGrid1.add(lvl1scorelbl2, 1, 3);
        leaderGrid1.add(lvl1scorelbl3, 1, 4);
        leaderGrid1.add(leaderUserlbl1, 0, 1);
        leaderGrid1.add(lvl1namelbl1, 0, 2);
        leaderGrid1.add(lvl1namelbl2, 0, 3);
        leaderGrid1.add(lvl1namelbl3, 0, 4);
        leaderGrid1.setAlignment(javafx.geometry.Pos.CENTER);
        leaderGrid1.getStylesheets().add("style.css");
        // Level 1 leaderboard borderpane
        BorderPane leaderBoarder1 = new BorderPane();
        leaderBoarder1.setTop(leaderlbl1);
        leaderBoarder1.setCenter(leaderGrid1);
        leaderBoarder1.setBottom(lvl1BackButton);
        leaderBoarder1.setAlignment(leaderlbl1, Pos.CENTER);
        leaderBoarder1.setAlignment(lvl1BackButton, Pos.CENTER);
        leaderboard1 = new Scene(leaderBoarder1, WINDOW_WIDTH, WINDOW_HEIGHT);
        leaderboard1.getStylesheets().add("style.css");
        // Titles and column headings for level 2 leaderboard.
        Label leaderlbl2 = new Label("Level 2 Leaderboard!");
        leaderlbl2.setId("titles");
        Label leaderUserlbl2 = new Label("Username");
        leaderUserlbl2.setId("titles");
        Label leaderScorelbl2 = new Label("Score");
        leaderScorelbl2.setId("titles");
        // Adds the top three names and scores into the level 2 leader board labels.
        Label lvl2scorelbl1 = new Label(Integer.toString(level2Leaderboard.getFileScore0()));
        lvl2scorelbl1.setId("titles");
        Label lvl2namelbl1 = new Label(level2Leaderboard.getFileName0());
        lvl2namelbl1.setId("titles");
        Label lvl2scorelbl2 = new Label(Integer.toString(level2Leaderboard.getFileScore1()));
        lvl2scorelbl2.setId("titles");
        Label lvl2namelbl2 = new Label(level2Leaderboard.getFileName1());
        lvl2namelbl2.setId("titles");
        Label lvl2scorelbl3 = new Label(Integer.toString(level2Leaderboard.getFileScore2()));
        lvl2scorelbl3.setId("titles");
        Label lvl2namelbl3 = new Label(level2Leaderboard.getFileName2());
        lvl2namelbl3.setId("titles");
        // Level 2 leader board GridPane - set up.
        GridPane leaderGrid2 = new GridPane();
        leaderGrid2.setPadding(new Insets(PADDING));
        leaderGrid2.setVgap(V_AND_H_GAP);
        leaderGrid2.setHgap(V_AND_H_GAP);
        // Level 2 leader board GridPane - adds the username and scores to gridpane.
        leaderGrid2.add(leaderScorelbl2, 1, 1);
        leaderGrid2.add(lvl2scorelbl1, 1, 2);
        leaderGrid2.add(lvl2scorelbl2, 1, 3);
        leaderGrid2.add(lvl2scorelbl3, 1, 4);
        leaderGrid2.add(leaderUserlbl2, 0, 1);
        leaderGrid2.add(lvl2namelbl1, 0, 2);
        leaderGrid2.add(lvl2namelbl2, 0, 3);
        leaderGrid2.add(lvl2namelbl3, 0, 4);
        leaderGrid2.setAlignment(javafx.geometry.Pos.CENTER);
        leaderGrid2.getStylesheets().add("style.css");
        // Level 2 leaderboard borderpane
        BorderPane leaderBoarder2 = new BorderPane();
        leaderBoarder2.setTop(leaderlbl2);
        leaderBoarder2.setCenter(leaderGrid2);
        leaderBoarder2.setBottom(lvl2BackButton);
        leaderBoarder2.setAlignment(leaderlbl2, Pos.CENTER);
        leaderBoarder2.setAlignment(lvl2BackButton, Pos.CENTER);
        leaderboard2 = new Scene(leaderBoarder2, WINDOW_WIDTH, WINDOW_HEIGHT);
        leaderboard2.getStylesheets().add("style.css");
        // Titles and column headings for level 2 leaderboard.
        Label leaderlbl3 = new Label("Level 3 Leaderboard!");
        leaderlbl3.setId("titles");
        Label leaderUserlbl3 = new Label("Username");
        leaderUserlbl3.setId("titles");
        Label leaderScorelbl3 = new Label("Score");
        leaderScorelbl3.setId("titles");
        // Adds the top three names and scores into the level 3 leader board labels.
        Label lvl3scorelbl1 = new Label(Integer.toString(level3Leaderboard.getFileScore0()));
        lvl3scorelbl1.setId("titles");
        Label lvl3namelbl1 = new Label(level3Leaderboard.getFileName0());
        lvl3namelbl1.setId("titles");
        Label lvl3scorelbl2 = new Label(Integer.toString(level3Leaderboard.getFileScore1()));
        lvl3scorelbl2.setId("titles");
        Label lvl3namelbl2 = new Label(level3Leaderboard.getFileName1());
        lvl3namelbl2.setId("titles");
        Label lvl3scorelbl3 = new Label(Integer.toString(level3Leaderboard.getFileScore2()));
        lvl3scorelbl3.setId("titles");
        Label lvl3namelbl3 = new Label(level3Leaderboard.getFileName2());
        lvl3namelbl3.setId("titles");
        // Level 3 leader board GridPane - set up.
        GridPane leaderGrid3 = new GridPane();
        leaderGrid3.setPadding(new Insets(PADDING));
        leaderGrid3.setVgap(V_AND_H_GAP);
        leaderGrid3.setHgap(V_AND_H_GAP);
        // Level 3 leader board GridPane - adds the username and scores to gridpane.
        leaderGrid3.add(leaderScorelbl3, 1, 1);
        leaderGrid3.add(lvl3scorelbl1, 1, 2);
        leaderGrid3.add(lvl3scorelbl2, 1, 3);
        leaderGrid3.add(lvl3scorelbl3, 1, 4);
        leaderGrid3.add(leaderUserlbl3, 0, 1);
        leaderGrid3.add(lvl3namelbl1, 0, 2);
        leaderGrid3.add(lvl3namelbl2, 0, 3);
        leaderGrid3.add(lvl3namelbl3, 0, 4);
        leaderGrid3.setAlignment(javafx.geometry.Pos.CENTER);
        leaderGrid3.getStylesheets().add("style.css");
        // Level 3 leaderboard borderpane
        BorderPane leaderBoarder3 = new BorderPane();
        leaderBoarder3.setTop(leaderlbl3);
        leaderBoarder3.setCenter(leaderGrid3);
        leaderBoarder3.setBottom(lvl3BackButton);
        leaderBoarder3.setAlignment(leaderlbl3, Pos.CENTER);
        leaderBoarder3.setAlignment(lvl3BackButton, Pos.CENTER);
        leaderboard3 = new Scene(leaderBoarder3, WINDOW_WIDTH, WINDOW_HEIGHT);
        leaderboard3.getStylesheets().add("style.css");
        // Setting the on actions for buttons - taking to another scene.
        leaderboardButton.setOnAction(e -> primaryStage.setScene(leaderboardSelect));
        lvl1Button.setOnAction(e -> primaryStage.setScene(leaderboard1));
        lvl2Button.setOnAction(e -> primaryStage.setScene(leaderboard2));
        lvl3Button.setOnAction(e -> primaryStage.setScene(leaderboard3));
        backButton4.setOnAction(e -> primaryStage.setScene(mainMenuScene));
        lvl1BackButton.setOnAction(e -> primaryStage.setScene(leaderboardSelect));
        lvl2BackButton.setOnAction(e -> primaryStage.setScene(leaderboardSelect));
        lvl3BackButton.setOnAction(e -> primaryStage.setScene(leaderboardSelect));
        // When the user hovers over a button, adds a boulder to the button
        // used for aesthetics.
        lvl1Button.setOnMouseEntered(e -> boulder11.setGraphic(new ImageView(boulder)));
        lvl1Button.setOnMouseExited(e -> boulder11.setGraphic(null));
        lvl2Button.setOnMouseEntered(e -> boulder12.setGraphic(new ImageView(boulder)));
        lvl2Button.setOnMouseExited(e -> boulder12.setGraphic(null));
        lvl3Button.setOnMouseEntered(e -> boulder13.setGraphic(new ImageView(boulder)));
        lvl3Button.setOnMouseExited(e -> boulder13.setGraphic(null));
        backButton4.setOnMouseEntered(e -> boulder14.setGraphic(new ImageView(boulder)));
        backButton4.setOnMouseExited(e -> boulder14.setGraphic(null));
        // Opens the main menu GUI.
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}