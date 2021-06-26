/**
 * This class is for the path of the game which is always walkable on by the user.
 * @author Richard James
 */
public class Path extends StandardCell {
    /**
     * Constructor for a path tile.
     * @param xPos x position
     * @param yPos y position
     */
    public Path(int xPos, int yPos) {
        super(xPos, yPos, false);
    }
}