import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

/**
 * Redesigned class for Player.
 * @author Richard James, Mindaugas Kavaliauskas and Vlad Stejeroiu
 */
public class Player {

    // X and Y position of player.
    private int positionX;
    private int positionY;

    // facing attribute.
    private char facing;

    // Username.
    private String username;

    // Player score counter.
    private int score;

    // Completion time counter.
    private long time;

    // Shows current level.
    private int currentLevel;

    // Number of tokens.
    private int dynamite = 0;

    // Axes gained by the player
    private ArrayList<Axe> axes;

    // Flippers and lava charm storage.
    private boolean flippers;
    private boolean lavaCharm;

    // Next Door Requirement placeholder for display door requirements.
    private int nextDoorReq = -1;

    // Images for facing.
    private Image north = new Image("file:assets/png/n_player.png");
    private Image east = new Image("file:assets/png/e_player.png");
    private Image south = new Image("file:assets/png/s_player.png");
    private Image west = new Image("file:assets/png/w_player.png");

    // Default image.
    private Image image = south;

    /**
     * Player constructor.
     * @param X x position
     * @param Y y position
     */
    public Player(int X, int Y) {
        this.positionX = X;
        this.positionY = Y;
        this.axes = new ArrayList<Axe>();
    }

    /**
     * Player constructor.
     * @param positionX the x position
     * @param positionY the y position
     * @param username the username
     * @param flippers true if has flippers
     * @param lavaCharm true if has lavaCharm
     * @param currentLevel the current level the player is on
     * @param dynamite the dynamite count
     * @param axes the axes ArrayList
     */
    public Player(int positionX, int positionY, String username, boolean flippers, boolean lavaCharm, int currentLevel,
            int score, int dynamite, ArrayList<Axe> axes) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.username = username;
        this.flippers = flippers;
        this.lavaCharm = lavaCharm;
        this.currentLevel = currentLevel;
        this.score = score;
        this.dynamite = dynamite;
        if (axes != null) {
            this.axes = axes;
        } else {
            this.axes = new ArrayList<Axe>();
        }
    }

    /**
     * Getter for x position.
     * @return positionX the x position
     */
    public int getX() {
        return this.positionX;
    }

    /**
     * Setter for x position.
     * @param x new x position
     */
    public void setX(int x) {
        this.positionX = x;
    }

    /**
     * Getter for y position.
     * @return positionY the y position
     */
    public int getY() {
        return this.positionY;
    }

    /**
     * Setter for y position.
     * @param y new y position
     */
    public void setY(int y) {
        this.positionY = y;
    }

    /**
     * Getter for score.
     * @return score profiles score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Setter for score.
     * @param score new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for time.
     * @return time time taken
     */
    public long getTime() {
        return this.time;
    }

    /**
     * Setter for time.
     * @param time new time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Teleport player method to new x and y.
     * @param x new x position
     * @param y new y position
     */
    public void teleport(int x, int y) {
        Audio sfx = new Audio();
        //Play sound when teleport
        try {
            sfx.crunch();
        } catch (Exception e) {
            e.getMessage();
        }
        this.positionX = x;
        this.positionY = y;
    }

    /**
     * Move up method. Changes facing direction and y position.
     */
    public void moveUp() {
        setFacing('n');
        this.positionY -= 1;
        this.score -= 10;
    }

    /**
     * Move down method. Changes facing direction and y position.
     */
    public void moveDown() {
        setFacing('s');
        this.positionY += 1;
        this.score -= 10;
    }

    /**
     * Move left method. Changes facing direction and x position.
     */
    public void moveLeft() {
        setFacing('w');
        this.positionX -= 1;
        this.score -= 10;
    }

    /**
     * Move right method. Changes facing direction and x position.
     */
    public void moveRight() {
        setFacing('e');
        this.positionX += 1;
        this.score -= 10;
    }

    /**
     * Setter for player image.
     * @param image new player image.
     */
    public void setPlayerImage(Image image) {
        this.image = image;
    }

    /**
     * Getter for facing direction.
     * @return facing the direction of the player
     */
    public char getFacing() {
        return this.facing;
    }

    /**
     * Setter for facing. Assigns image.
     * @param facing new facing direction
     */
    public void setFacing(char facing) {
        this.facing = facing;
        switch (this.facing) {
        case 'n':
            setPlayerImage(north);
            break;
        case 'e':
            setPlayerImage(east);
            break;
        case 's':
            setPlayerImage(south);
            break;
        case 'w':
            setPlayerImage(west);
            break;
        default:
            setPlayerImage(south);
            break;
        }
    }

    /**
     * Adds one to dynamite count.
     */
    public void addDynamite() {
        if(dynamite < 9) {
            this.dynamite++;
        }
    }

    /**
     * Getter for dynamite count.
     * @return dynamite the dynamite count
     */
    public int dynamiteNum() {
        return this.dynamite;
    }

    /**
     * Getter for flippers.
     * @return true if has flippers; otherwise false
     */
    public boolean hasFlippers() {
        return this.flippers;
    }

    /**
     * Getter for LavaCharm.
     * @return true if has LavaCharm; otherwise false
     */
    public boolean hasLavaCharm() {
        return this.lavaCharm;
    }

    /**
     * Calculates how many axes of a certain colour the player has.
     * @param colour colour wanted
     * @return count amount of certain colour
     */
    public int quantityColourAxe(String colour) {
        int count = 0;
        Iterator<Axe> axesIterator = axes.iterator();
        while (axesIterator.hasNext()) {
            if (axesIterator.next().getAxeColour().equals(colour)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Adds an item to inventory based on what was picked up.
     * @param item collectible item
     */
    public void addItem(Collectible item) {
        this.score += 100;
        if (item instanceof Flippers) {
            this.flippers = true;
        } else if (item instanceof LavaCharm) {
            this.lavaCharm = true;
        } else if (item instanceof Axe) {
            axes.add((Axe) item);
        } else if (item instanceof Dynamite) {
            addDynamite();
        }
        // Plays the picking up audio
        Audio sfx = new Audio();
        try {
            sfx.collect();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Resets the game to its base state and resets the collectable file for the
     * user.
     * @param level level being restarted
     * @throws FileNotFoundException file cannot be loaded
     */
    public void restartGame(World level) throws FileNotFoundException {
        this.score -= 500;
        Scanner in;
        FileWriter fw;
        try {
            in = new Scanner(new File(this.currentLevel + "fresh.txt"));
            fw = new FileWriter(this.getUsername() + ".txt", false);
            fw.write(in.nextLine() + this.getUsername() + "," + this.flippers + "," + this.lavaCharm + ","
                    + this.currentLevel + ",10000,0\n");
            while (in.hasNextLine()) {
                fw.write(in.nextLine() + "\n");
            }
            in.close();
            fw.close();
        } catch (Exception f) {
            System.out.println("Can't write.");
        }
        setPlayerImage(south);
        this.axes = new ArrayList<Axe>();
        this.dynamite = 0;
        this.flippers = false;
        this.lavaCharm = false;
        level.make(this.getUsername());
        Scanner defXY = new Scanner(new File(this.currentLevel + "fresh.txt"));
        String xy = defXY.nextLine();
        defXY.close();
        String[] newXY = xy.split(",");
        this.setX(Integer.parseInt(newXY[0]));
        this.setY(Integer.parseInt(newXY[1]));
    }

    /**
     * Checks if the player can "open" the door.
     * @param door the door it's trying to move through
     * @return true or false depending if possible.
     */
    public boolean checkDoor(Door door) {
        if (door instanceof Log) {
            for (Axe axe : axes) {
                if (axe.getAxeColour().equals((String) door.getRequirement())) {
                    return true;
                }
            }
        } else if (door instanceof Boulder) {
            nextDoorReq = (int) door.getRequirement();
            if (this.dynamiteNum() >= (int) door.getRequirement()) {
                nextDoorReq = -1;
                return true;
            }
        }
        if (door.getXPos() == this.getX() + 1) {
            setFacing('e');
        } else if (door.getXPos() == this.getX() - 1) {
            setFacing('w');
        } else if (door.getYPos() == this.getY() + 1) {
            setFacing('s');
        } else if (door.getYPos() == this.getY() - 1) {
            setFacing('n');
        }
        return false;
    }

    /**
     * Tries to open the door and removes the axe from the players inventory if it can be opened.
     * @param door Door the player is trying to move through.
     * @return the new Path cell if the player can move through the door.
     */
    public Path headbuttDoor(Door door) {
        if (door instanceof Log) {
            Log log = (Log) door;
            Iterator<Axe> axesIterator = axes.iterator();
            while (axesIterator.hasNext()) {
                if (axesIterator.next().getAxeColour().equals(log.getColour())) {
                    Audio sfx = new Audio();
                    try {
                        sfx.chop();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    axesIterator.remove();
                    break;
                }
            }
            return log.openDoor();
        } else {
            Boulder boulder = (Boulder) door;
            Audio sfx = new Audio();
            try {
                sfx.explosion();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return boulder.openDoor();
        }
    }

    /**
     * Checks if the player can move to the cell it is trying to move to.
     * @param newMove the cell the player is trying to move to
     * @param level the world
     * @param newFacing where the player would be facing if it successfully move
     * @return true if move is possible; otherwise false
     */
    public boolean validMove(StandardCell newMove, World level, char newFacing) {
        Audio sfx = new Audio();
        nextDoorReq = -1;
        if (newMove instanceof Door) {
            return checkDoor((Door) newMove);
        } else if (newMove instanceof Teleporter) {
            if (validTeleport((Teleporter) newMove, level, newFacing)) {
                Teleporter t = (Teleporter) newMove;
                t.teleportPlayer(this, level);
                return true;
            } else {
                return false;
            }
        } else if (newMove instanceof Goal) {
            return true;
        } else if (newMove instanceof Path) {
            try {
                sfx.walk();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return true;
        } else {
            try {
                sfx.bashWall();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
    }

    /**
     * Checks if the player can teleport through the teleporter to a valid location.
     * @param t the teleporter the player is interacting with
     * @param level the world
     * @param newFacing where the player would be facing if the move commenced
     * @return true if valid move, otherwise false
     */
    private boolean validTeleport(Teleporter t, World level, char newFacing) {
        int newX;
        int newY;
        switch (newFacing) {
        case 'n':
            newX = t.getConnectedTeleporter().getXPos();
            newY = t.getConnectedTeleporter().getYPos() - 1;
            if (this.validMove(level.getCell(newX, newY), level, newFacing)) {
                return true;
            }
            break;
        case 'e':
            newX = t.getConnectedTeleporter().getXPos() + 1;
            newY = t.getConnectedTeleporter().getYPos();
            if (this.validMove(level.getCell(newX, newY), level, newFacing)) {
                return true;
            }
            break;
        case 's':
            newX = t.getConnectedTeleporter().getXPos();
            newY = t.getConnectedTeleporter().getYPos() + 1;
            if (this.validMove(level.getCell(newX, newY), level, newFacing)) {
                return true;
            }
            break;
        case 'w':
            newX = t.getConnectedTeleporter().getXPos() - 1;
            newY = t.getConnectedTeleporter().getYPos();
            if (this.validMove(level.getCell(newX, newY), level, newFacing)) {
                return true;
            }
            break;
        }
        return false;
    }

    /**
     * Draws the player to the screen.
     * @param gc GraphicsContext
     */
    public void draw(GraphicsContext gc) {
        if (nextDoorReq == -1) {
            gc.drawImage(image, 3 * 64, 3 * 64);
        } else {
            gc.drawImage(image, 3 * 64, 3 * 64);
            gc.drawImage(new Image("file:assets/png/dynamite_" + nextDoorReq + ".png"), 24, 250);
        }
        System.out.println("Current location: " + positionX + ", " + positionY);
    }

    /**
     * Getter for username.
     * @return username the player username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for username.
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for current level.
     * @return current level
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Setter for current level.
     * @param currentLevel the current level
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

	/**
	 * Alters the toString method for player.
	 * @return String of the x,y coordinates, the username, if they have flippers
	 * and lava charm, the current level, their score, dynamite number and all their axes
	 */
    public String toString() {
        String rString = this.getX() + "," + this.getY() + ",";
        rString += this.username + ",";
        rString += this.flippers + ",";
        rString += this.lavaCharm + ",";
        rString += this.currentLevel + ",";
        rString += this.score + ",";
        rString += dynamiteNum() + ",";
        for (Axe a : axes) {
            rString += a.getAxeColour() + ",";
        }
        return rString;
    }
}