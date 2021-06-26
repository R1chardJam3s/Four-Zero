/**
 * The class for the Boulders in the game.
 * @param <Integer> the key requirement to open the door.
 * @author Richard James
 */
public class Boulder extends Door<Integer> {
	
	/**
	 * Constructor for a boulder.
	 * @param xPos x position
	 * @param yPos y position
	 * @param dynamite count of dynamite needed to blow up boulder.
	 */
	public Boulder(int xPos, int yPos, Integer dynamite) {
		super(xPos, yPos, dynamite);
	}

	/**
	 * Getter for dynamite count needed.
	 * @return count integer
	 */
	public Integer getDynamiteCount() {
		return this.key;
	}
	
	/**
	 * Alters the toString method for boulder.
	 * @return String of the x,y coordinates, Boulder and the dynamite count needed
	 */
	@Override
	public String toString() {
		return this.getXPos() + "," + this.getYPos() + ",Boulder," + this.getDynamiteCount();
	}
}
