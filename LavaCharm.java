import javafx.scene.image.Image;
/**
 * The class for the LavaCharm item in the game.
 * @author Richard James
 */
public class LavaCharm extends Collectible {
    /**
     * Constructor for LavaCharm item.
     * @param xPos x position
     * @param yPos y position
     */
    public LavaCharm(int xPos, int yPos) {
        super(xPos, yPos);
        this.setImage(new Image("file:assets/png/lava_charm.png"));
    }

    /**
     * Collects the item.
     * @return this current LavaCharm
     */
    public LavaCharm collect() {
        return this;
    }
    
	/**
	 * Alters the toString method for LavaCharm.
	 * @return String of the x,y coordinates, LavaCharm
	 */
    @Override
    public String toString() {
        return this.getXPos() + "," + this.getYPos() + ",LavaCharm";
    }
}