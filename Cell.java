import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * Class for a cell in the game.
 * @author Vlad Stejeroiu
 */
public abstract class Cell {

	//Varibles for stored common attributes; image, x coordinate and y coordinate.
	private Image image;
	private int xPos;
	private int yPos;
	
	/**
	 * Cell constructor.
	 * @param xPos x position of cell on grid
	 * @param yPos y position of cell on grid
	 */
	public Cell(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Draw method to draw the cell on the screen.
	 * @param gc GraphicsContext for the game
	 * @param x lower x bound, x value of far left of the screen
	 * @param y lower y bound, y value of top of the screen
	 */
	public void draw(GraphicsContext gc, int x, int y) {
		gc.drawImage(image, (xPos - x) * 64, (yPos - y) * 64);
	}
	
	/**
	 * Getter for x position.
	 * @return x the x position
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * Setter for x position.
	 * @param xPos new x position
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Getter for y position.
	 * @return y the y position
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * Setter for y position.
	 * @param yPos new y position
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Setter for image.
	 * @param newImage new image
	 */
	public void setImage(Image newImage) {
		this.image = newImage;
	}

	/**
	 * Getter for image.
	 * @return image the current image.
	 */
	public Image getImage() {
		return this.image;
	}
}
