/**
 * The abstract class for doors.
 * @param <T> door type, can be String or Integer depending on if it's a log or boulder.
 * @author Richard James
 */
public abstract class Door<T> extends StandardCell {

	//The key for the door.
	protected T key;
	
	/**
	 * The constructor for the Door.
	 * @param xPos x position
	 * @param yPos y position
	 * @param key the key for the door
	 */
	public Door(int xPos, int yPos, T key) {
		super(xPos, yPos, true);
		this.key = key;
	}
	
	/**
	 * Setter for door requirement.
	 * @param key the new key
	 */
	public void setRequirement(T key) {
		this.key = key;
	}
	
	/**
	 * Getter for requirement.
	 * @return key the key requirement
	 */
	public T getRequirement() {
		return key;
	}
	
	/**
	 * Method to open the door.
	 * @return newPath cell to replace current door cell
	 */
	public Path openDoor() {
		Path newPath = new Path(this.getXPos(), this.getYPos());
		return newPath;
	}

	/**
	 * Abstract toString() method.
	 */
	public abstract String toString();
}
