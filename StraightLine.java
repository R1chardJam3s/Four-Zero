import javafx.scene.image.Image;

/**
 * Class for StraightLine enemies.
 * @author William Playle-de Vries
 */
public class StraightLine extends Enemy {

    // Enemy images for Straight Line.
    private Image south = new Image("file:assets/png/sl_down.png");
    private Image north = new Image("file:assets/png/sl_up.png");
    private Image west = new Image("file:assets/png/sl_left.png");
    private Image east = new Image("file:assets/png/sl_right.png");

    /**
     * Constructor for Straightline enemy.
     * @param facing the direction the straightline is facing
     * @param positionX the x coordinate of the straightline enemy
     * @param positionY the y coordinate of the straightline enemy
     * @param image the image of the straightline enemy
     */
    public StraightLine(char facing, int positionX, int positionY, Image image) {
        super(facing, positionX, positionY, image);
        imageAssignment();
    }

    /**
     * Sets the image for the enemy depending on what way the enemy is facing.
     */
    private void imageAssignment() {
        if (this.facing == 'n') {
            this.setImage(north);
        } else if (this.facing == 'e') {
            this.setImage(east);
        } else if (this.facing == 's') {
            this.setImage(south);
        } else if (this.facing == 'w') {
            this.setImage(west);
		}
	}

	/**
	 * Call this to update the coordinates for StraightLine to next move to.
	 * @param level
	 */
	@Override
	public void move(World level, Player player) {

		if (this.checkAhead(this.positionX, this.positionY, this.facing, level)) {
			this.turnAround(this.facing);
		} else {
			PositionXY next = this.straightPath(this.facing);
			super.setX(next.getX());
			super.setY(next.getY());
		}
		imageAssignment();
	}

	/**
	 * Depending on facing, computes where the next place the enemy should be.
	 * @param facing
	 * @return the next coords in the form of PositionXY.class
	 */
	public PositionXY straightPath(char facing) {
		PositionXY nextStep = new PositionXY(super.getX(), super.getY());

		switch (facing) {

		case 'n':
			nextStep = new PositionXY(super.getX(), super.getY() - 1);
			break;
		case 'e':
			nextStep = new PositionXY(super.getX() + 1, super.getY());
			break;
		case 's':
			nextStep = new PositionXY(super.getX(), super.getY() + 1);
			break;
		case 'w':
			nextStep = new PositionXY(super.getX() - 1, super.getY());
			break;
		default:
			System.out.println(
					"ERROR StraightLine straightPath(): no facing (" + this.positionX + ", " + this.positionY + ")");
		}

		return nextStep;
	}

	/**
	 * Turns the enemy if it need to. 
	 * @param facing facing direction
	 */
	public void turnAround(char facing) {
		switch (facing) {
		case 'n':
			this.facing = 's';
			break;
		case 'e':
			this.facing = 'w';
			break;
		case 's':
			this.facing = 'n';
			break;
		case 'w':
			this.facing = 'e';
			break;
		default:
			System.out.println(
					"ERROR StraightLine turnAround(): no facing (" + this.positionX + ", " + this.positionY + ")");

		}
	}

	/**
	 * Checks if wall is ahead.
	 * @param x x position
	 * @param y y position
	 * @param facing current facing
	 * @param level world
	 * @return boolean true if cannot move ahead
	 */
	public boolean checkAhead(int x, int y, char facing, World level) {
		switch (facing) {
		case 'n':
			if (level.getCell(x, y - 1).getSolid()) {
				return true;
			}
			if(level.hasCollectible(x, y - 1)) {
				return true;
			}
			if(level.getCell(x, y - 1) instanceof Lava || level.getCell(x, y - 1) instanceof Water) {
				return true;
			}
			break;

		case 'e':
			if (level.getCell(x + 1, y).getSolid()) {
				return true;
			}
			if(level.hasCollectible(x + 1, y)) {
				return true;
			}
			if(level.getCell(x + 1, y) instanceof Lava || level.getCell(x + 1, y) instanceof Water) {
				return true;
			}
			break;

		case 's':
			if (level.getCell(x, y + 1).getSolid()) {
				return true;
			}
			if(level.hasCollectible(x, y + 1)) {
				return true;
			}
			if(level.getCell(x, y + 1) instanceof Lava || level.getCell(x, y + 1) instanceof Water) {
				return true;
			}
			break;

		case 'w':
			if (level.getCell(x - 1, y).getSolid()) {
				return true;
			}
			if(level.hasCollectible(x - 1, y)) {
				return true;
			}
			if(level.getCell(x - 1, y) instanceof Lava || level.getCell(x - 1, y) instanceof Water) {
				return true;
			}
			break;

		default:
			System.out.println(
					"ERROR StraightLine checkAhead: no facing (" + this.positionX + ", " + this.positionY + ")");
			break;

		}
		return false;
	}

	/**
	 * Alters the toString method for straight line enemy.
	 * @return String of the x,y coordinates, Straight direction the enemy is facing
	 */
	@Override
	public String toString() {
		return this.positionX + "," + this.positionY + ",Straight," + this.getFacing();
	}
}