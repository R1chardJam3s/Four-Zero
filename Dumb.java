import javafx.scene.image.Image;

/**
 * Class for Dumb enemies.
 * @author Mindaugas Kavaliauskas and Vlad Stejeroiu
 */
public class Dumb extends Enemy {

	//currentCell is used to store where the enemy should move to
	private CNode currentCell;

	//Player X and Y values
	private int playerX;
	private int playerY;

	//Enemy X and Y values
	private int enemyX;
	private int enemyY;

	//Difference in X and Y axis (+/-)
	private int xDifference;
	private int yDifference;

	//Difference in X and Y 
	private int rawX;
	private int rawY;

	//Enemy images for dumb targeting.
    private Image south = new Image("file:assets/png/dumb_down.png");
    private Image north = new Image("file:assets/png/dumb_up.png");
    private Image west = new Image("file:assets/png/dumb_left.png");
    private Image east = new Image("file:assets/png/dumb_right.png");
	
	/**
	 * Constructor for Dumb
	 * @param facing
	 * @param positionX
	 * @param positionY
	 * @param image
	 */
	public Dumb(char facing, int positionX, int positionY, Image image) {
		super(facing, positionX, positionY, image);
	}

	/**
	 * Assigns images to enemy based on position
	 * @param path new cell location
	 */
	private void imageAssignment(CNode path) {
        if(path.getXPos() > this.positionX) {
            this.setImage(east);
            this.setFacing('e');
        } else if(path.getXPos() < this.positionX) {
            this.setImage(west);
            this.setFacing('w');
        } else if(path.getYPos() > this.positionY) {
            this.setImage(south);
            this.setFacing('s');
        } else if(path.getYPos() < this.positionY) {
            this.setImage(north);
            this.setFacing('n');
        }
	}

	/**
	 * Moves the enemy.
	 * @param level the world
	 * @param player the player
	 */
	@Override
	public void move(World level, Player player) {
		this.currentCell = new CNode(this.positionX, this.positionY);
		moveToPlayer(level, player);
	}

	/**
	 * Calculates the enemy movement.
	 * @param level the world
	 * @param player the player
	 */
	public void moveToPlayer(World level, Player player) {
		this.playerX = player.getX();
		this.playerY = player.getY();
		this.enemyX = this.currentCell.getXPos();
		this.enemyY = this.currentCell.getYPos();
		xDifference = getXDifference(playerX, enemyX);
		yDifference = getYDifference(playerY, enemyY);
		if((enemyX != playerX) || (enemyY != playerY))
		{
			if(rawXDifference(xDifference) > rawYDifference(yDifference)) {
				if(xDifference > 0)	{
					this.currentCell.setXPos(this.currentCell.getXPos() + 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setXPos(this.currentCell.getXPos() - 1);
						this.currentCell.setYPos(this.currentCell.getYPos());
					}
				} else {
					this.currentCell.setXPos(this.currentCell.getXPos() - 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setXPos(this.currentCell.getXPos() + 1);
						this.currentCell.setYPos(this.currentCell.getYPos());
					}
				}
			}
			else if(rawXDifference(xDifference) < rawYDifference(yDifference)) {
				if(yDifference > 0)	{
					this.currentCell.setYPos(this.currentCell.getYPos() + 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setXPos(this.currentCell.getXPos());
						this.currentCell.setYPos(this.currentCell.getYPos() - 1);
					}
				} else {
					this.currentCell.setYPos(this.currentCell.getYPos() - 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setXPos(this.currentCell.getXPos());
						this.currentCell.setYPos(this.currentCell.getYPos() + 1);
					}
				}
			}
			else {
				if (enemyY > playerY) {
					this.currentCell.setYPos(this.currentCell.getYPos() -1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setXPos(this.currentCell.getXPos());
						this.currentCell.setYPos(this.currentCell.getYPos() + 1);
					}
				} else if(enemyX > playerX) {
					this.currentCell.setXPos(this.currentCell.getXPos() - 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setYPos(this.currentCell.getYPos());
						this.currentCell.setXPos(this.currentCell.getXPos() + 1);
					}
				} else if(enemyX < playerX)	{
					this.currentCell.setXPos(this.currentCell.getXPos() + 1);
					if(checkAhead(level)) {
						imageAssignment(currentCell);
						super.setX(currentCell.getXPos());
						super.setY(currentCell.getYPos());
					} else {
						this.currentCell.setYPos(this.currentCell.getYPos());
						this.currentCell.setXPos(this.currentCell.getXPos() - 1);
					}
				}else {
					imageAssignment(currentCell);
					super.setX(currentCell.getXPos());
					super.setY(currentCell.getYPos());
				}
				imageAssignment(currentCell);
				super.setX(currentCell.getXPos());
				super.setY(currentCell.getYPos());
			}
		}
	}

	/**
	 * Checks if the Cell it's trying to move into is avaliable to be moved into.
	 * @param level the world
	 * @return true if it is possible to move into
	 */
	public boolean checkAhead(World level) {
		if(level.getCell(this.currentCell.getXPos(), this.currentCell.getYPos()) instanceof Path && 
		!level.hasCollectible(this.currentCell.getXPos(), this.currentCell.getYPos()) &&
		!(level.getCell(this.currentCell.getXPos(), this.currentCell.getYPos()) instanceof Lava || 
		level.getCell(this.currentCell.getXPos(), this.currentCell.getYPos()) instanceof Water)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate raw X difference.
	 * @param xDifference current difference, can be positive or negative
	 * @return the abs of xDifference
	 */
	private int rawXDifference(int xDifference)	{
		rawX = Math.abs(xDifference);
		return rawX;
	}

	/**
	 * Calculate raw Y difference.
	 * @param yDifference current difference, can be positive or negative
	 * @return the abs of yDifference
	 */
	private int rawYDifference(int yDifference)	{
		rawY = Math.abs(yDifference);
		return rawY;
	}

	/**
	 * Calculate xDifference.
	 * @param playerX player X position
	 * @param enemyX enemy X position
	 * @return difference in X value
	 */
	public int getXDifference(int playerX, int enemyX) {
		xDifference = playerX - enemyX;
		return xDifference; 
	}

	/**
	 * Calculate Y difference.
	 * @param playerY player Y position
	 * @param enemyY enemy Y position
	 * @return difference in Y position
	 */
	public int getYDifference(int playerY, int enemyY) {
		yDifference = playerY - enemyY;
		return yDifference; 
	}

	/**
	 * toString() method that writes the enemy attributes to a string for loading.
	 * @return String of data
	 */
	@Override
	public String toString() {
		return this.positionX + "," + this.positionY + ",Dumb," + this.getFacing();
	}
}