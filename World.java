import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.control.*;

/**
 * This class is used to keep track of the state of the world in the game. It
 * holds the map, enemies and items as well as updates them on player movement.
 * @author Richard James
 */
public class World {

    // Width and Height of the world.
    private int height;
    private int width;

    // WorldReader instance.
    private WorldReader wr;

    // map
    private StandardCell[][] map;

    // Enemies array which holds the enemies which are updated in each turn.
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
    private Collectible toRemove = null;

    private Goal goal;

    /**
     * Makes the world based on the file that is read in.
     * @throws FileNotFoundException file not present
     */
    public void make(String filename) throws FileNotFoundException {
        wr = new WorldReader();
        wr.readFile(filename + ".txt");
        this.map = wr.getMap();
        this.width = wr.getWidth();
        this.height = wr.getHeight();
        this.enemies = wr.getEnemies();
        this.collectibles = wr.getCollectibles();
        this.goal = wr.getGoal();
    }

    /**
     * Getter for player.
     * @return player
     */
    public Player getPlayer() {
        return wr.getPlayer();
    }

    /**
     * Getter for a cell in the map given x and y.
     * @param xPos x position
     * @param yPos y position
     * @return StandardCell
     */
    public StandardCell getCell(int xPos, int yPos) {
        return map[xPos][yPos];
    }

    /**
     * Getter for width of map
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for height
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for ArrayList of collectibles.
     * @return collectible ArrayList
     */
    public ArrayList<Collectible> getCollectibles() {
        return this.collectibles;
    }

    /**
     * Method for checking if the x and y position has a collectible present.
     * @param x x position
     * @param y y position
     * @return true if colletible is present
     */
    public boolean hasCollectible(int x, int y) {
        for (Collectible c : collectibles) {
            if (c.getX() == x && c.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates to world and the entities within it.
     * @param player player
     * @param level  world
     * @throws FileNotFoundException file not found
     */
    public void updateWorld(Player player) throws FileNotFoundException {
        if (toRemove != null) {
            collectibles.remove(toRemove);
            toRemove = null;
        }
        if (map[player.getX()][player.getY()] instanceof Goal) {
            Goal g = (Goal) map[player.getX()][player.getY()];
            g.nextLevel(this, player);
        }
        if (map[player.getX()][player.getY()] instanceof Door) {
            map[player.getX()][player.getY()] = player.headbuttDoor((Door) map[player.getX()][player.getY()]);
            if (map[player.getX() + 1][player.getY()] instanceof Path) {
                map[player.getX()][player.getY()].setImage(new Image("file:assets/png/000111000.png"));
            } else {
                map[player.getX()][player.getY()].setImage(new Image("file:assets/png/010010010.png"));
            }
        }
        if (map[player.getX()][player.getY()] instanceof Water && !player.hasFlippers()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // pop up message
            alert.setTitle("You drowned");
            alert.setHeaderText("You died, so the level restarted");
            alert.setContentText("Can't walk on water without flippers");
            alert.show();
            player.restartGame(this);
            Audio sfx = new Audio();
            try {
                sfx.death();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        if (map[player.getX()][player.getY()] instanceof Lava && !player.hasLavaCharm()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // pop up message
            alert.setTitle("You melted");
            alert.setHeaderText("You died, so the level restarted");
            alert.setContentText("Can't walk on lava without your Lava charm");
            alert.show();
            player.restartGame(this);
            try {
                this.make(player.getCurrentLevel() + "fresh.txt");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
            Audio sfx = new Audio();
            try {
                sfx.death();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        for (Enemy e : enemies) {
            e.checkDeath(player, this); // To catch when a player and enemy are next to eachother and the player goes
            // in the direction of said enemy
            e.move(this, player);
            e.checkDeath(player, this);
        }
        for (Collectible c : collectibles) {
            if (player.getX() == c.getXPos() && player.getY() == c.getYPos()) {
                player.addItem(c);
                c.setImage(null);
                toRemove = c;
            }
        }

    }

    /**
     * Draw method to draw the world. 
     * @param gc GraphicsContext
     * @param lowerX lower x bound
     * @param upperX upper x bound
     * @param lowerY lower y bound
     * @param upperY upper y bound
     */
    public void draw(GraphicsContext gc, int lowerX, int upperX, int lowerY, int upperY) {
        for (int x = lowerX; x <= upperX; x++) {
            for (int y = lowerY; y <= upperY; y++) {
                getCell(x, y).draw(gc, lowerX, lowerY);
            }
        }
        for (Enemy e : enemies) {
            if (e.positionX >= lowerX && e.positionX <= upperX && e.positionY >= lowerY && e.positionY <= upperY) {
                e.draw(gc, lowerX, lowerY);
            }
        }
        for (Collectible c : collectibles) {
            if (c.getXPos() >= lowerX && c.getXPos() <= upperX && c.getYPos() >= lowerY && c.getYPos() <= upperY) {
                c.draw(gc, lowerX, lowerY);
            }
        }
    }

    /**
     * Getter for doors.
     * @return ArrayList of doors
     */
    public ArrayList<Door> getDoors() {
        ArrayList<Door> doors = new ArrayList<Door>();
        ;
        for (StandardCell[] a : map) {
            for (StandardCell c : a) {
                if (c instanceof Door) {
                    doors.add((Door) c);
                }
            }
        }
        return doors;
    }

    /**
     * Getter for teleporters.
     * @return ArrayList of teleporters
     */
    public ArrayList<Teleporter> getTeleporters() {
        ArrayList<Teleporter> tps = new ArrayList<Teleporter>();
        for (StandardCell[] a : map) {
            for (StandardCell c : a) {
                if (c instanceof Teleporter) {
                    tps.add((Teleporter) c);
                }
            }
        }
        return tps;
    }

    /**
     * Writes thes save of the world into a file.
     * @param player player
     * @throws IOException Exception if file isn't created
     */
    public void writeSave(Player player) throws IOException {
        File saveFile = new File(player.getUsername() + ".txt");
        FileWriter w = new FileWriter(saveFile, false);
        w.write(player.toString() + "\n");
        for (Collectible c : collectibles) {
            w.write(c.toString() + "\n");
        }
        for (Enemy e : enemies) {
            w.write(e.toString() + "\n");
        }
        for (Door d : getDoors()) {
            w.write(d.toString() + "\n");
        }
        for (Teleporter t : getTeleporters()) {
            w.write(t.toString() + "\n");
        }
        w.write(goal.toString() + "\n");
        w.close();
    }
}