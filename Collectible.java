/**
 * The class for the Collectible items.
 * @author Vlad Stejeroiu
 */
public abstract class Collectible extends Cell {

	/**
	 * Constructor for the Collectible items.
	 * @param xPos x coordinate
	 * @param yPos y coordinate
	 */
	public Collectible(int xPos, int yPos) {
		super(xPos, yPos);
	}

	/**
	 * Getter for x position.
	 * @return x the x position
	 */
	public int getX() {
		return super.getXPos();
	}

	/**
	 * Getter for y position.
	 * @return y the y position
	 */
	public int getY() {
		return super.getYPos();
	}

	/**
	 * Returns the current collectible stood on to the players inventory.
	 * @return colllectible
	 */
	public abstract Collectible collect();

	/**
	 * Abstract toString() method.
	 */
	public abstract String toString();
}