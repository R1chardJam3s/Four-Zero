/**
 * This class is for the teleporter in the game.
 * @author Richard James
 */
public class Teleporter extends StandardCell {

    // The teleporter the teleporter sends the player too
    private Teleporter connectedTeleporter;

    /**
     * Constructor for teleporter class.
     * @param xPos x position of the teleporter
     * @param yPos y position of the teleporter
     */
    public Teleporter(int xPos, int yPos) {
        super(xPos, yPos, false);
    }

    /**
     * Constructor for teleporter class.
     * @param xPos x position
     * @param yPos y position
     * @param c teleporter that it is linked to.
     */
    public Teleporter(int xPos, int yPos, Teleporter c) {
        super(xPos, yPos, false);
        setConnectedTeleporter(c);
    }

    /**
     * Set the connected teleporter the teleporter.
     * @param t new connected teleporter
     */
    public void setConnectedTeleporter(Teleporter t) {
        this.connectedTeleporter = t;
        if (t.getConnectedTeleporter() != this) {
            t.setConnectedTeleporter(this);
        }
    }

    /**
     * Gets the connected teleporter.
     * @return connectedTeleporter connected teleporter
     */
    public Teleporter getConnectedTeleporter() {
        return this.connectedTeleporter;
    }

    /**
     * Teleporting the player, changes the x,y coordinates of player.
     * @param p player
     * @param w world
     */
    public void teleportPlayer(Player p, World w) {
        // set player location to teleporter location + directional movement
        p.teleport(this.getConnectedTeleporter().getXPos(), this.getConnectedTeleporter().getYPos());
    }

    /**
	 * Alters the toString method for teleporter.
	 * @return String of the x,y coordinates, Teleporter, and the x,y coordinated
	 * of the connected teleporter
	 */
    @Override
    public String toString() {
        return this.getXPos() + "," + this.getYPos() + ",Teleporter," + this.getConnectedTeleporter().getXPos() + ","
                + this.getConnectedTeleporter().getYPos();
    }
}