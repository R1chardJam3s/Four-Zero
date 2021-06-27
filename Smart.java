import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class for smart targeting enemies.
 * @author Richard James and Mindaugas Kavaliauskas
 */
public class Smart extends Enemy {

    //Stores a map of where is walkable. Used locally.
    private CNode[][] map;

    //OpenList for storing nodes on the map that need to be visiting.
    private ArrayList <CNode> openList = new ArrayList <CNode> ();

    //ClosedList for storing nodes on the map that have been visited.
    private ArrayList <CNode> closedList = new ArrayList <CNode> ();

    //The optimal path for the enemy.
    private ArrayList <CNode> path = new ArrayList <CNode> ();

    //Target found boolean.
    private boolean targetFound;

    //Enemy images for smart targeting.
    private Image south = new Image("file:assets/png/smart_down.png");
    private Image north = new Image("file:assets/png/smart_up.png");
    private Image west = new Image("file:assets/png/smart_left.png");
    private Image east = new Image("file:assets/png/smart_right.png");

    /**
     * Smart targeting enemy constructor.
     * @param facing facing value
     * @param posX x position
     * @param posY y position
     * @param image enemy image
     */
    public Smart(char facing, int posX, int posY, Image image) {
        super(facing, posX, posY, image);
    }

    /**
     * Populates map based on the maps state
     * @param level the world
     */
    private void populateMap(World level) {
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                if (level.getCell(x, y) instanceof Path && !(level.getCell(x, y) instanceof Lava || level.getCell(x, y) instanceof Water)) {
                    map[x][y] = new CNode(x, y, -1);
                }
            }
        }
        for (Collectible c: level.getCollectibles()) {
            map[c.getX()][c.getY()] = null;
        }
    }

    /**
     * Calculates the heuristic value of all CNodes in map.
     * @param player player
     */
    private void calcH(Player player) {
        for (CNode[] a: map) {
            for (CNode c: a) {
                if (c != null) {
                    c.setH(Math.abs(c.getXPos() - player.getX()) + Math.abs(c.getYPos() - player.getY()));
                }
            }
        }
    }

    /**
     * Calculates the F value for each CNode when called.
     * @param current the CNode it's calculating the F value for
     */
    private void calcF(CNode current) {
        current.setF(current.getG() + current.getH());
    }

    /**
     * Checks the CNodes on the map for the path to the target, and handles the situation that occurs if the target isn't reachable.
     * @param current current CNode
     */
    private void addSurrounding(CNode current) {
        while (!targetFound) {
            openList.remove(current);
            closedList.add(current);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 || y == 0) {
                        CNode next = map[current.getXPos() + x][current.getYPos() + y];
                        if (next != null && !closedList.contains(next)) {
                            if (next.isTarget()) {
                                next.setParent(current);
                                closedList.add(next);
                                targetFound = true;
                            } else {
                                if (current.getG() + 10 < next.getG() && next.getG() != 0) {
                                    next.setParent(current);
                                    next.setG(next.getG() + 10);
                                } else if (next.getG() == 0) {
                                    next.setParent(current);
                                    next.setG(next.getG() + 10);
                                }
                                calcF(next);
                                if (!openList.contains(next)) {
                                    openList.add(next);
                                }
                            }
                        }
                    }
                }
            }
            Collections.sort(openList);
            if (!openList.isEmpty()) {
                addSurrounding(openList.get(0));
            } else {
                closedList.clear();
                randomMove();
                targetFound = true;
            }
        }
    }

    /**
     * Generates a random move if the target isn't reachable.
     */
    private void randomMove() {
        Random r = new Random();
        int xChange = r.nextInt(2) - 1;
        int yChange = r.nextInt(2) - 1;
        if (xChange == 0 && yChange == 0) {
            xChange += 1;
        }
        CNode randMove = map[this.positionX + xChange][this.positionY + yChange];
        if (randMove != null) {
            closedList.add(randMove);
        } else {
            randomMove();
        }

    }

    /**
     * Creates the path 
     * @param current current CNode
     */
    private void getPath(CNode current) {
        //System.out.println("P: " + current);
        path.add(0, current);
        if (current.getParent() != null) {
            getPath(current.getParent());
        }
    }

    /**
     * Assigns the images to the entity based on where it is facing.
     */
    private void imageAssignment() {
        if (path.get(1).getXPos() > this.positionX) {
            this.setImage(east);
            this.setFacing('e');
        } else if (path.get(1).getXPos() < this.positionX) {
            this.setImage(west);
            this.setFacing('w');
        } else if (path.get(1).getYPos() > this.positionY) {
            this.setImage(south);
            this.setFacing('s');
        } else if (path.get(1).getYPos() < this.positionY) {
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
        map = new CNode[level.getWidth()][level.getHeight()];
        targetFound = false;
        openList.clear();
        closedList.clear();
        path.clear();
        populateMap(level);
        map[player.getX()][player.getY()] = new CNode(player.getX(), player.getY(), -1);
        map[player.getX()][player.getY()].setTarget();
        calcH(player);
        openList.add(map[this.positionX][this.positionY]);
        addSurrounding(openList.get(0));
        getPath(closedList.get(closedList.size() - 1));
        System.out.println("Current Pos: " + this.positionX + ", " + this.positionY);
        imageAssignment();
        this.setX(path.get(1).getXPos());
        this.setY(path.get(1).getYPos());
    }

	/**
	 * Alters the toString method for smart enemy.
	 * @return String of the x,y coordinates, Smart and the direction the enemy is facing
	 */
    @Override
    public String toString() {
        return this.positionX + "," + this.positionY + ",Smart," + this.getFacing();
    }
}