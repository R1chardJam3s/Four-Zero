/**
 * This class is for the walls in the game.
 * @author Richard James
 */
public class Wall extends StandardCell {

    /**
     * Constructor for a Wall
     * @param xPos x position of wall on map
     * @param yPos y position of wall on map
     */
    public Wall(int xPos, int yPos) {
        super(xPos, yPos, true);
    }
}