import javafx.scene.image.Image;
/**
 * The class for the Dynamite in the game.
 * @author Vlad Stejeroiu
 */
public class Dynamite extends Collectible {

	/**
	 * Constructor for Dynamite.
	 * @param xPos x coordinate
	 * @param yPos y coordinate
	 */
	public Dynamite(int xPos, int yPos) { 
		super(xPos, yPos);
		this.setImage(new Image("file:assets/png/dynamite.png"));
	}
	
	/**
	 * Pickup dynamite.
	 * @return this dynamite object
	 */
	public Dynamite collect() {
		return this;
	}
	
	/**
	 * Alters the toString method for dynamite.
	 * @return String of the x,y coordinates and Dynamite
	 */
	@Override
	public String toString() {
		return this.getXPos() + "," + this.getYPos() + ",Dynamite";
	}
}