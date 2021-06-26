/**
 * Class used for returning X and Y values in class form
 * @author William Playle-de Vries
 */
public class PositionXY {
	
	//X and Y coordinates
    private int X;
    private int Y;

    /**
     * Constructor for PositionXY.
     * @param X x position
     * @param Y y position
     */
    public PositionXY(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Getter for x position.
     * @return x position
     */
    public int getX() {
        return X;
    }

    /**
     * Getter for y position.
     * @return y position
     */
    public int getY() {
        return Y;
    }

    /**
     * Setter for x position.
     * @param x new x position
     */
    public void setX(int x) {
        this.X = x;
    }

    /**
     * Setter for y position.
     * @param y new y position
     */
    public void setY(int y) {
        this.Y = y;
    }

    /**
     * Setter for x and y position.
     * @param x new x position
     * @param y new y position
     */
    public void setXY(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}