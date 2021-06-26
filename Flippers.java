import javafx.scene.image.Image;
/**
 * The class for the flippers item in the game.
 * @author Vlad Stejeroiu
 */
public class Flippers extends Collectible {

    /**
     * Constructor for flippers item.
     * @param xPos x position
     * @param yPos y position
     */
    public Flippers(int xPos, int yPos) {
        super(xPos, yPos);
        this.setImage(new Image("file:assets/png/flippers.png"));
    }

    /**
     * Collects the flippers.
     * @return this Flippers object
     */
    public Flippers collect() {
        return this;
    }

    /**
	 * Alters the toString method for flippers.
	 * @return String of the x,y coordinates and Flippers
	 */
    @Override
    public String toString() {
        return this.getXPos() + "," + this.getYPos() + ",Flippers";
    }
}