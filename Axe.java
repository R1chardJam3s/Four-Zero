import javafx.scene.image.Image;
/**
 * The class for the Axes in the game.
 * @author Richard James
 */
public class Axe extends Collectible {
	
	//Colour of the axe
	private String colour;

	/**
	 * Constructor for an Axe.
	 * @param posX x position
	 * @param posY y position
	 */
	public Axe(int posX, int posY, String colour) {
		super(posX, posY);
		setAxeColour(colour);
		this.setImage(new Image("file:assets/png/" + this.colour + "_axe.png"));
	}
	/**
	 * Getter for colour.
	 * @return colour colour of the axe
	 */
	public String getAxeColour() {
		return colour;
	}

	/**
	 * Setter for colour.
	 * @param colour colour of the axe
	 */
	public void setAxeColour(String colour) {
		this.colour = colour;
	}

	/**
	 * Returns the axe to the player to get put into the inventory.
	 * @return this the axe
	 */
	@Override
	public Axe collect() {
		return this;
	}
	
	/**
	 * Alters the toString method for boulder.
	 * @return String of the x,y coordinates, Axe and the colour of the axe
	 */
	@Override
	public String toString() {
		return this.getXPos() + "," + this.getYPos() + ",Axe," +  this.getAxeColour();
	}
}