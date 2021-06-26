import java.io.FileNotFoundException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.control.*;

/**
 * Class for enemies in the game.
 * @author William Playle-de Vries
 */
public abstract class Enemy {

    //String to say what enemy is doing. Output in console if sayMoveStatement is true.
    protected String moveStatement;

    //Image for the enemy
    private Image image;

    //Facing attribute
    protected char facing;

    //X and Y position
    protected int positionX;
    protected int positionY;

    /**
     * Constructor for enemy class.
     * @param facing facing direction (up, down, left, right)
     * @param positionX x position of enemy
     * @param positionY y position of enemy
     * @param image starting image associated with the enemy object
     */
    public Enemy(char facing, int positionX, int positionY, Image image) {
        this.facing = facing;
        this.positionX = positionX;
        this.positionY = positionY;
        this.image = image;
    }

    /**
     * Drawing enemy to the screen.
     * @param gc GraphicsContext
     * @param lX lower X bound of screen
     * @param lY lower Y bound of screen
     */
    public void draw(GraphicsContext gc, int lX, int lY) {
        gc.drawImage(image, (positionX - lX) * 64, (positionY - lY) * 64);
    }

    /**
     * Setter for enemy image.
     * @param path image of the path
     */
    public void setImage(Image path) {
        this.image = path;
    }

    /**
     * Abstract move method for enemy.
     * @param level World level data
     * @param player Player location
     */
    public abstract void move(World level, Player player);

    /**
     * Getter for facing direction.
     * @return facing the facing direction of enemey
     */
    public char getFacing() {
        return facing;
    }

    /**
     * Setter for facing direction.
     * @param facing new facing direction
     */
    public void setFacing(char facing) {
        this.facing = facing;
    }

    /**
     * Getter for x position.
     * @return positionX the x position
     */
    public int getX() {
        return positionX;
    }

    /**
     * Getter for y position.
     * @return positionY the y position
     */
    public int getY() {
        return positionY;
    }

    /**
     * Setter for x position.
     * @param x new x position
     */
    public void setX(int x) {
        this.positionX = x;
    }

    /**
     * Setter for y position.
     * @param y new y position
     */
    public void setY(int y) {
        this.positionY = y;
    }

    /**
     * Checks if the player is on the same location as the enemy,
     * shows an alert box and restarts the level if it is.
     * @param player player object for location data
     * @param level world object
     */
    public void checkDeath(Player player, World level) throws FileNotFoundException {
        if (this.positionX == player.getX() && this.positionY == player.getY()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // pop up message
            alert.setTitle("Enemy encountered");
            alert.setHeaderText("You died, so the level restarted");
            alert.setContentText("Don't let the enemies catch you!");
            alert.show();
            player.restartGame(level);
            //Plays death sound
            Audio sfx = new Audio();
            try {
                sfx.death();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * abstract toString() method.
     * @return String of data
     */
    public abstract String toString();
}