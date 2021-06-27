/**
 * A class for standard cell types in the game.
 * @author Richard James
 */
public abstract class StandardCell extends Cell {

    //Solid state for if the player can step on the cell
    private boolean isSolid;

    /**
     * Constructs the StandardCell.
     * @param xPos x coordinate
     * @param yPos y coordinate
     * @param isSolid boolean if the player can interact
     */
    public StandardCell(int xPos, int yPos, boolean isSolid) {
        super(xPos, yPos);
        this.isSolid = isSolid;
    }

    /**
     * Setter for isSolid.
     * @param isSolid new isSolid state
     */
    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    /**
     * Getter for isSolid.
     * @return isSolid true if is solid; false otherwise
     */
    public boolean getSolid() {
        return this.isSolid;
    }
}