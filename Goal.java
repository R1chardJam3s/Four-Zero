import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.File;

/**
 * Class for the goal.
 * @author Richard James
 */
public class Goal extends StandardCell {

    // Level the goal trasnfers to.
    private int nextLevel;

	/**
	 * Constructor for an Goal.
	 * @param posX x position
	 * @param posY y position
	 * @param nextLevel next level number
	 */
    public Goal(int xPos, int yPos, int nextLevel) {
        super(xPos, yPos, false);
        this.nextLevel = nextLevel;
    }

    /**
     * Getter for next level
     * @return nextLevel the next level number
     */
    public int getNextLevel() {
        return this.nextLevel;
    }

    /**
     * Takes player to the next level upon completion and back to the main menu,
     * if it is the last level, calculates the score and adds it to the leaderboard. 
     * @param level the level the player will be taken to
     * @param player the player being taken to the next level
     * @throws FileNotFoundException
     */
    public void nextLevel(World level, Player player) throws FileNotFoundException {
        System.out.println("Score: " + player.getScore());
        if (player.getCurrentLevel() == 3) {
            // End game.
            System.out.println("We're in the endgame now.");
            try {
                addScore(player);
                backToMenu();
            } catch (IOException e) {
                System.out.println("Can't write to leaderboard.");
            }
        } else {
            Audio sfx = new Audio();
            try {
                sfx.woosh();
            } catch (Exception e) {
                e.getMessage();
            }
            Profile p = Profile.getProfile(player.getUsername());
            try {
            	//Adds the level completed to their profile on the profiles file
                if (!p.getLevels().contains(this.nextLevel)) {
                    p.addLevelToFile(this.nextLevel);
                }
                player.setScore((int)(player.getScore() - (int) player.getTime() / 500));//Calculating score
                addScore(player);
                //Taking player to next level
                player.setCurrentLevel(this.nextLevel);
                player.restartGame(level);
            } catch (IOException e) {
                System.out.println("No file");
            }
        }
    }

    /**
     * Closes the game and loads the main menu GUI when last level is finished.
     */
    private void backToMenu() {
        Stage s = new Stage();
        try {
            new MainMenu().start(s);
            FourZero.curStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the players score to the leaderboards file for that level.
     * @param player the Player that is used
     * @throws IOException
     */
    private void addScore(Player player) throws IOException {
        File lb = new File("leaderboards/" + player.getCurrentLevel() + ".txt");
        FileWriter fw = new FileWriter(lb, true);
        fw.write(player.getUsername() + "," + player.getScore() + "\n");
        fw.close();
    }

    /**
	 * Alters the toString method for goal.
	 * @return String of the x,y coordinates, Goal and the next level number
	 */
    public String toString() {
        return this.getXPos() + "," + this.getYPos() + ",Goal," + this.nextLevel;
    }
}