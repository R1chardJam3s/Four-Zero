import javafx.scene.image.Image;

/**
 * Wall Following enemy hugs the wall it is spawned next to, favouring the left
 * wall when spawned.
 * @author William Playle-de Vries
 */
public class WallFollowing extends Enemy {

    // Attribute to store whether or not the Enemy follows the left or right wall
    private boolean followsLeft;

    // Attribute as to whether it is the enemy's first move. Used in determining
    // followsLeft
    private boolean firstMove;

    // Attribute as to whether or not the enemy has just turned
    private boolean turned;

    // Enemy images for Wall Following.
    private Image south = new Image("file:assets/png/wf_down.png");
    private Image north = new Image("file:assets/png/wf_up.png");
    private Image west = new Image("file:assets/png/wf_left.png");
    private Image east = new Image("file:assets/png/wf_right.png");

    /**
     * Constructor for WallFollowing.
     * @param facing direction the wall following enemy is facing
     * @param positionX x position of the enemy
     * @param positionY y position of the enemy
     * @param image image of the enemy
     */
    public WallFollowing(char facing, int positionX, int positionY, Image image) {
        super(facing, positionX, positionY, image);
        firstMove = true;
        imageAssignment();
    }

	/**
	 * Assign images based on facing.
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
	 * Moves the enemy.
	 * @param level the world
	 * @param player the player
	 */
	@Override
	public void move(World level, Player player) {

		// Reset the move statement every move
		moveStatement = this + ":\n\t(" + this.positionX + ", " + this.positionY + ")\n\tfacing:" + this.facing;

		if (firstMove) {
			if (this.checkLeft(this.positionX, this.positionY, this.facing, level)) {
				this.followsLeft = true;
				System.out.println("WallFollowing now following left");
			} else if (this.checkRight(this.positionX, this.positionY, this.facing, level)) {
				this.followsLeft = false;
				System.out.println("WallFollowing now following right");
			} else {
				System.out.println(
						"ERROR: Can't find wall to set direction (" + this.positionX + ", " + this.positionY + ")");
			}
			firstMove = false;
		}
		PositionXY next = this.wallPath(this.facing, level);
		imageAssignment();
		super.setX(next.getX());
		super.setY(next.getY());
	}

	/**
	 * Calculates where the enemy should move to.
	 * @param facing facing direction
	 * @param level the world
	 * @return coordinates to move to
	 */
	public PositionXY wallPath(char facing, World level) {
		PositionXY nextStep = new PositionXY(this.positionX, this.positionY);
		if (this.followsLeft) { // When following the left hand wall
			// Case 0 When there's no route to the left or ahead
			if (checkLeft(this.positionX, this.positionY, facing, level)
					&& checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 0");
				turnGoLeft(facing, false); // Turn right
				// CASE 1 When there's no route left, but there is route ahead
			} else if (checkLeft(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 1");
				switch (facing) {
					case 'n':
						nextStep.setY(this.positionY - 1); // Move forwards (north)
						break;
					case 's':
						nextStep.setY(this.positionY + 1); // Move forwards (south)
						break;
					case 'e':
						nextStep.setX(this.positionX + 1); // Move forwards (east)
						break;
					case 'w':
						nextStep.setX(this.positionX - 1); // Move forwards (west)
						break;
					default:
						System.out.println("ERROR WallFollowing WallPath() Case 1: no facing (" + this.positionX + ", "
								+ this.positionY + ")");
						break;
				}
				this.turned = false;
				// Case 2 When there's a route left and no route ahead
			} else if (!checkLeft(this.positionX, this.positionY, facing, level)
					&& checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 2");
				turnGoLeft(facing, true); // Turn left
				this.turned = true;
				// Case 3 When there's a route left and a route ahead
			} else if (!checkLeft(this.positionX, this.positionY, facing, level)
					&& !checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 3");
				if (turned) {
					switch (facing) {
						case 'n':
							nextStep.setY(this.positionY - 1); // Move forwards (north)
							break;
						case 's':
							nextStep.setY(this.positionY + 1); // Move forwards (south)
							break;
						case 'e':
							nextStep.setX(this.positionX + 1); // Move forwards (east)
							break;
						case 'w':
							nextStep.setX(this.positionX - 1); // Move forwards (west)
							break;
						default:
							System.out.println("ERROR WallFollowing WallPath() Case 3: no facing (" + this.positionX + ", "
									+ this.positionY + ")");
							break;
					}
					this.turned = false;
				} else {
					turnGoLeft(facing, true); // Turn Left
					this.turned = true;
				}
			}
			// If it follows the righthand wall
		} else {
			// Case 4 When there's no route to the right or ahead
			if (checkRight(this.positionX, this.positionY, facing, level)
					&& checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 4");
				turnGoLeft(facing, true); // Turn left
				// Case 5 When there's no route right, but there is route ahead
			} else if (checkRight(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 5");
				switch (facing) {
					case 'n':
						nextStep.setY(this.positionY - 1); // Move forwards (north)
						break;
					case 's':
						nextStep.setY(this.positionY + 1); // Move forwards (south)
						break;
					case 'e':
						nextStep.setX(this.positionX + 1); // Move forwards (east)
						break;
					case 'w':
						nextStep.setX(this.positionX - 1); // Move forwards (west)
						break;
					default:
						System.out.println("ERROR WallFollowing WallPath() Case 5: no facing (" + this.positionX + ", "
								+ this.positionY + ")");
						break;
				}
				this.turned = false;

				// Case 6 When there's a route right and no route ahead
			} else if (!checkRight(this.positionX, this.positionY, facing, level)
					&& checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 6");
				turnGoLeft(facing, false); // Turn right
				this.turned = true;
				// Case 7 When there's a route left and a route ahead
			} else if (!checkRight(this.positionX, this.positionY, facing, level)
					&& !checkAhead(this.positionX, this.positionY, facing, level)) {
				// System.out.println("Path Case 7");
				if (turned) {
					switch (facing) {
						case 'n':
							nextStep.setY(this.positionY - 1); // Move forwards (north)
							break;
						case 's':
							nextStep.setY(this.positionY + 1); // Move forwards (south)
							break;
						case 'e':
							nextStep.setX(this.positionX + 1); // Move forwards (east)
							break;
						case 'w':
							nextStep.setX(this.positionX - 1); // Move forwards (west)
							break;
						default:
							System.out.println("ERROR WallFollowing WallPath() Case 7: no facing (" + this.positionX + ", "
									+ this.positionY + ")");
							break;
					}
					this.turned = false;
				} else {
					turnGoLeft(facing, false); // Turn Right
					this.turned = true;
				}
			}
		}
		return nextStep;
	}

	/**
	 * Turns the enemy left or right based on left . (true turns left, false turns
	 * right).
	 * @param facing facing value
	 * @param left true is going left
	 */
	public void turnGoLeft(char facing, boolean left) {
		switch (this.facing) {
			case 'n':
				if (left) { // Turn left
					this.facing = 'w';
				} else { // Turn right
					this.facing = 'e';
				}
				break;
			case 's':
				if (left) { // Turn left
					this.facing = 'e';
				} else { // Turn right
					this.facing = 'w';
				}
				break;
			case 'e':
				if (left) { // Turn left
					this.facing = 'n';
				} else { // Turn right
					this.facing = 's';
				}
				break;
			case 'w':
				if (left) { // Turn left
					this.facing = 's';
				} else { // Turn right
					this.facing = 'n';
				}
				break;
			default:
				System.out.println("ERROR WallFollowing turnGoLeft() cannot turn: no facing");
				break;
		}
	}

	/**
	 * Checks if a wall is to the left.
	 * @param x x position
	 * @param y y position
	 * @param facing facing direction
	 * @param level the world
	 * @return true if wall to left
	 */
	public boolean checkLeft(int x, int y, char facing, World level) {
		switch (facing) {
			case 'n':
				if (level.getCell(x - 1, y).getSolid()) {
					// System.out.println("Solid leftn");
					return true;
				}
				if(level.hasCollectible(x - 1, y)) {
					return true;
				}
				if(level.getCell(x - 1, y) instanceof Lava || level.getCell(x - 1, y) instanceof Water) {
					return true;
				}
				break;

			case 'e':
				if (level.getCell(x, y - 1).getSolid()) {
					// System.out.println("Solid lefte");
					return true;
				}
				if(level.hasCollectible(x, y - 1)) {
					return true;
				}
				if(level.getCell(x, y - 1) instanceof Lava || level.getCell(x, y - 1) instanceof Water) {
					return true;
				}
				break;

			case 's':
				if (level.getCell(x + 1, y).getSolid()) {
					// System.out.println("Solid lefts");
					return true;
				}
				if(level.hasCollectible(x + 1, y)) {
					return true;
				}
				if(level.getCell(x + 1, y) instanceof Lava || level.getCell(x + 1, y) instanceof Water) {
					return true;
				}
				break;

			case 'w':
				if (level.getCell(x, y + 1).getSolid()) {
					// System.out.println("Solid leftw");
					return true;
				}
				if(level.hasCollectible(x, y + 1)) {
					return true;
				}
				if(level.getCell(x, y + 1) instanceof Lava || level.getCell(x, y + 1) instanceof Water) {
					return true;
				}
				break;

			default:
				System.out.println("ERROR WallFollowing:checkLeft() due to no facing");
				break;
		}
		return false;
	}

	/**
	 * Checks if a wall is to the right.
	 * @param x x position
	 * @param y y position
	 * @param facing facing direction
	 * @param level the world
	 * @return true if wall to left
	 */
	public boolean checkRight(int x, int y, char facing, World level) {
		switch (facing) {

			case 'n':
				if (level.getCell(x + 1, y).getSolid()) {
					System.out.println("Solid right");
					return true;
				}
				if(level.hasCollectible(x + 1, y)) {
					return true;
				}
				if(level.getCell(x + 1, y) instanceof Lava || level.getCell(x + 1, y) instanceof Water) {
					return true;
				}
				break;

			case 'e':
				if (level.getCell(x, y + 1).getSolid()) {
					System.out.println("Solid right");
					return true;
				}
				if(level.hasCollectible(x, y + 1)) {
					return true;
				}
				if(level.getCell(x, y + 1) instanceof Lava || level.getCell(x, y + 1) instanceof Water) {
					return true;
				}
				break;

			case 's':
				if (level.getCell(x - 1, y).getSolid()) {
					System.out.println("Solid right");
					return true;
				}
				if(level.hasCollectible(x - 1, y)) {
					return true;
				}
				if(level.getCell(x - 1, y) instanceof Lava || level.getCell(x - 1, y) instanceof Water) {
					return true;
				}
				break;

			case 'w':
				if (level.getCell(x, y - 1).getSolid()) {
					System.out.println("Solid right");
					return true;
				}
				if(level.hasCollectible(x, y - 1)) {
					return true;
				}
				if(level.getCell(x, y - 1) instanceof Lava || level.getCell(x, y - 1) instanceof Water) {
					return true;
				}
				break;

			default:
				System.out.println("ERROR line 277 cannot complete WallFollowing:checkLeft() due to no facing");
				break;

		}
		return false;

	}

	/**
	 * Checks if a wall is to the foreward.
	 * @param x x position
	 * @param y y position
	 * @param facing facing direction
	 * @param level the world
	 * @return true if wall to left
	 */
	public boolean checkAhead(int x, int y, char facing, World level) {
		switch (facing) {
			case 'n':
				if (level.getCell(x, y - 1).getSolid()) { // Check for walls
					// System.out.println("Solid ahead");
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
					// System.out.println("Solid ahead");
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
					// System.out.println("Solid ahead");
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
					// System.out.println("Solid ahead");
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
				System.out.println("ERROR WallFollowing checkAhead() cannot complete  due to no facing");
				break;
		}
		return false;
	}

	/**
	 * toString() to print WallFollowing attributes for saving.
	 * @return String of data
	 */
	@Override
	public String toString() {
		return this.positionX + "," + this.positionY + ",WallFollowing," + this.getFacing();
	}
}
