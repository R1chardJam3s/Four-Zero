/**
 * The class for the Log in the game.
 * @author Richard James
 * @param <String> The type that the key is.
 */
public class Log extends Door <String> {

    /**
     * Constructor for the Log door object.
     * @param xPos x position
     * @param yPos y position
     * @param colour the colour axe requirement to break the log.
     */
    public Log(int xPos, int yPos, String colour) {
        super(xPos, yPos, colour);
    }

    /**
     * Getter for colour axe needed.
     * @return colour the colour of the axe
     */
    public String getColour() {
        return this.key;
    }
    
	/**
	 * Alters the toString method for log.
	 * @return String of the x,y coordinates, Log and the colour of the log
	 */
    @Override
    public String toString() {
        return this.getXPos() + "," + this.getYPos() + ",Log," + this.getColour();
    }
}