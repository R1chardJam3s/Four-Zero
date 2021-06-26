/**
 * A class for creating nodes used to store pathing values with smart targetting enemies.
 * @author Richard James and Mindaugas Kavaliauskas
 */
public class CNode extends StandardCell implements Comparable {
    
    //The current (g + h), used to calculate cost of movement to that cell to the player.
    private int f;

    //Movement cost to this cell from starting cell.
    private int g = 0;

    //Heuristic value - the amount of cells between this cell and the target cell
    //Calculated by (diff x + diff y)
    private int h;

    //Parent node.
    private CNode parent;

    //Boolean for if this is the desired endpoint of the algorithm
    private boolean target = false;

    /**
     * Constructor for CNode.
     * @param xPos x position
     * @param yPos y position
     * @param h heuristic value - defaults to -1
     */
    public CNode(int xPos, int yPos, int h) {
        super(xPos, yPos, false);
        this.h = h;
    }

    /**
     * Second constructor for CNode with no h value.
     * @param xPos x position
     * @param yPos y position
     */
    public CNode(int x, int y) {
        super(x,y, false);
    }
    
    /**
     * Checker for if the CNode is the target location.
     * @return target boolean
     */
    public boolean isTarget() {
        return this.target;
    }

    /**
     * Setter to make the cell the target. Is called once per movement call of enemy.
     */
    public void setTarget() {
        this.target = true;
    }

    /**
     * Setter for F value.
     * @param f new F value
     */
    public void setF(int f) {
        this.f = f;
    }

    /**
     * Setter for H value.
     * @param h new H value
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Setter for G value.
     * @param g new G value.
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Setter for parent.
     * @param parent new parent CNode
     */
    public void setParent(CNode parent) {
        this.parent = parent;
    }

    /**
     * Getter for F value.
     * @return f f value of CNode
     */
    public int getF() {
        return this.f;
    }

    /**
     * Getter for H value.
     * @return h H value of CNode
     */
    public int getH() {
        return this.h;
    }

    /**
     * Getter for G value.
     * @return g G value of CNode
     */
    public int getG() {
        return this.g;
    }

    /**
     * Getter for parent.
     * @return parent parent of CNode
     */
    public CNode getParent() {
        return this.parent;
    }

    /**
	 * Compares two CNodes to see which one has the greatest f value.
	 * @param o CNode to compare to
	 * @return f value minus the f value of compared CNode
	 */
    @Override
	public int compareTo(Object o) {
        CNode other = (CNode) o;
        return this.f - other.getF();
    }
    
    /**
	 * Alters the toString method for CNode.
	 * @return String of the x,y coordinates
	 */
    @Override
    public String toString() {
        return "(" + this.getXPos() + "," + this.getYPos() + ")";
    }
}