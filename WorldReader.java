import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Class for generating the WorldReader for the world.
 * @author Richard James
 */
public class WorldReader {

    //Current X and Y values.
    private int currentX;
    private int currentY;

    //Local copy of the map as it's being read in.
    private StandardCell[][] map;

    //Player
    private Player player;

    //Width and Height of map
    private int width;
    private int height;

    //ArrayList for collectibles and enemies.
    private ArrayList <Collectible> collectibles = new ArrayList <Collectible> ();
    private ArrayList <Enemy> enemies = new ArrayList <Enemy> ();

    private Goal goal;

    //Walls iamge array for random assignment
    private Image[] walls = new Image[3];

    //The base PATH for all images.
    private final String PATH = "file:assets/png/";

    /**
     * Constructor for WorldReader. Sets currentX and Y to 0 and assigns the images.
     */
    public WorldReader() {
        this.currentX = 0;
        this.currentY = 0;
        walls[0] = new Image("file:assets/png/basic_tree_with_grass.png");
        walls[1] = new Image("file:assets/png/bush_with_grass.png");
        walls[2] = new Image("file:assets/png/pine_tree_with_grass.png");
    }

    /**
     * Getter for width
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
     * Getter for collectibles
     * @return collectibles ArrayList
     */
    public ArrayList <Collectible> getCollectibles() {
        return this.collectibles;
    }

    /**
     * Getter for enemies.
     * @return enemies ArrayList
     */
    public ArrayList <Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * Getter for map
     * @return map
     */
    public StandardCell[][] getMap() {
        return this.map;
    }

    /**
     * Getter for player.
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter for Goal
     * @return goal
     */
    public Goal getGoal() {
        return this.goal;
    }

    /**
     * Reader for the file. Reads player info then constructs the player.
     * Calls other relevant methods.
     * @param dataFilename filename with the player info and entity info
     * @throws FileNotFoundException file not found
     */
    public void readFile(String dataFilename) throws FileNotFoundException {
        Scanner file = new Scanner(new File(dataFilename));
        String info = file.nextLine();
        Scanner playerInfo = new Scanner(info);
        playerInfo.useDelimiter(",");
        int x = playerInfo.nextInt();
        int y = playerInfo.nextInt();
        String username = playerInfo.next();
        boolean flippers = playerInfo.nextBoolean();
        boolean lavaCharm = playerInfo.nextBoolean();
        int currentLevel = playerInfo.nextInt();
        int score = playerInfo.nextInt();
        int dynamiteCount = playerInfo.nextInt();
        ArrayList <Axe> axes = new ArrayList <Axe> ();
        while (playerInfo.hasNext()) {
            axes.add(new Axe(0, 0, playerInfo.next()));
        }
        playerInfo.close();
        if (axes.isEmpty()) {
            player = new Player(x, y, username, flippers, lavaCharm, currentLevel, score, dynamiteCount, null);
        } else {
            player = new Player(x, y, username, flippers, lavaCharm, currentLevel, score, dynamiteCount, axes);
        }
        readFile(currentLevel + ".txt", file);
    }

    /**
     * Reader for file to create map.
     * @param levelFilename filename for level layout
     * @param mapInfo Scanner that isn't finished reading
     * @throws FileNotFoundException file not found.
     */
    public void readFile(String levelFilename, Scanner mapInfo) throws FileNotFoundException {
        Scanner in = new Scanner(new File(levelFilename));
        String size = in .nextLine();
        String[] wh = size.split(",");
        this.width = Integer.parseInt(wh[0]);
        this.height = Integer.parseInt(wh[1]);
        map = new StandardCell[width][height];
        while ( in .hasNext()) {
            String currentLine = in .nextLine();
            for (int i = 0; i < width; i++) {
                char c = currentLine.charAt(i);
                if (c == '#') {
                    map[currentX][currentY] = new Wall(currentX, currentY);
                } else if (c == 'W') {
                    map[currentX][currentY] = new Water(currentX, currentY);
                } else if (c == 'L') {
                    map[currentX][currentY] = new Lava(currentX, currentY);
                } else {
                    map[currentX][currentY] = new Path(currentX, currentY);
                }
                this.currentX++;
            }
            this.currentX = 0;
            this.currentY++;
        } in .close();
        setAttributes(mapInfo);
        improvedImages();
    }

    /**
     * Reader for map entites doors, teleporters, collectibles, enemies.
     * @param in scanner for file
     */
    private void setAttributes(Scanner in ) {
        System.out.println( in .hasNext());
        while ( in .hasNext()) {
            String data = in .nextLine();
            System.out.println(data);
            Scanner attr = new Scanner(data);
            attr.useDelimiter(",");
            int x = attr.nextInt();
            int y = attr.nextInt();
            String type = attr.next();
            if (type.equals("Log")) {
                map[x][y] = new Log(x, y, attr.next());
            } else if (type.equals("Boulder")) {
                map[x][y] = new Boulder(x, y, attr.nextInt());
            } else if (type.equals("Teleporter")) {
                int connX = attr.nextInt();
                int connY = attr.nextInt();
                map[connX][connY] = new Teleporter(connX, connY);
                map[x][y] = new Teleporter(x, y, (Teleporter) map[connX][connY]);
            } else if (type.equals("Goal")) {
                map[x][y] = new Goal(x, y, attr.nextInt());
                goal = (Goal) map[x][y];
            } else {
                addEntity(x, y, type, attr);
            }
            attr.close();
        } in .close();
    }

    /**
     * Adds the entity on the map if it is none static, collectible and enemies.
     * @param x x position
     * @param y y position
     * @param type type of the entity
     * @param attr the rest of the scanner for the line
     */
    private void addEntity(int x, int y, String type, Scanner attr) {
        if (type.equals("Axe")) {
            collectibles.add(new Axe(x, y, attr.next()));
        } else if (type.equals("Dynamite")) {
            collectibles.add(new Dynamite(x, y));
        } else if (type.equals("Straight")) {
            this.enemies.add(new StraightLine(attr.next().charAt(0), x, y, null));
        } else if (type.equals("WallFollowing")) {
            enemies.add(new WallFollowing(attr.next().charAt(0), x, y, null));
        } else if (type.equals("Dumb")) {
            enemies.add(new Dumb(attr.next().charAt(0), x, y, new Image("file:assets/png/dumb_left.png")));
        } else if (type.equals("Smart")) {
            this.enemies.add(new Smart(attr.next().charAt(0), x, y, new Image("file:assets/png/smart_left.png")));
        } else if (type.equals("Flippers")) {
            collectibles.add(new Flippers(x, y));
        } else if (type.equals("LavaCharm")) {
            collectibles.add(new LavaCharm(x, y));
        }
    }

    /**
     * Algorithm for path image assignment.
     * Uses binary naming system to determine what path should be displayed based on the surrounding cells
     * @return the map with images assigned.
     */
    private StandardCell[][] improvedImages() {
        Random r = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                StandardCell c = map[x][y];
                String name = "";
                if (c instanceof Wall) {
                    c.setImage(walls[r.nextInt(3)]);
                } else if (c instanceof Water) {
                    if (map[x][y + 1] instanceof Path || map[x][y - 1] instanceof Path) {
                        name += "ver_water";
                    } else if (map[x - 1][y] instanceof Path || map[x + 1][y] instanceof Path) {
                        name += "hor_water";
                    }
                    map[x][y].setImage(new Image(PATH + name + ".png"));
                } else if (c instanceof Lava) {
                    if (map[x][y + 1] instanceof Path || map[x][y - 1] instanceof Path) {
                        name += "ver_lava";
                    } else if (map[x - 1][y] instanceof Path || map[x + 1][y] instanceof Path) {
                        name += "hor_lava";
                    }
                    map[x][y].setImage(new Image(PATH + name + ".png"));
                } else if (c instanceof Log) {
                    if (map[x][y + 1] instanceof Path || map[x][y - 1] instanceof Path) {
                        name += "ver_log_";
                    } else if (map[x - 1][y] instanceof Path && map[x + 1][y] instanceof Path) {
                        name += "hor_log_";
                    }
                    Log log = (Log) c;
                    map[x][y].setImage(new Image(PATH + name + log.getRequirement() + ".png"));
                } else if (c instanceof Boulder) {
                    if (map[x][y + 1] instanceof Path || map[x][y - 1] instanceof Path) {
                        name += "ver_boulder";
                    } else if (map[x - 1][y] instanceof Path || map[x + 1][y] instanceof Path) {
                        name += "hor_boulder";
                    }
                    map[x][y].setImage(new Image(PATH + name + ".png"));
                } else if (c instanceof Teleporter) {
                    if (map[x - 1][y] instanceof Path) {
                        name += "left_";
                    }
                    if (map[x + 1][y] instanceof Path) {
                        name += "right_";
                    }
                    if (map[x][y - 1] instanceof Path) {
                        name += "top_";
                    }
                    if (map[x][y + 1] instanceof Path) {
                        name += "bottom_";
                    }
                    map[x][y].setImage(new Image(PATH + name + "shroom.png"));
                } else if (c instanceof Goal) {
                    if (map[x + 1][y] instanceof Path) {
                        name += "goal_right";
                    } else if (map[x - 1][y] instanceof Path) {
                        name += "goal_left";
                    } else if (map[x][y + 1] instanceof Path) {
                        name += "goal_down";
                    } else if (map[x][y - 1] instanceof Path) {
                        name += "goal_up";
                    }
                    map[x][y].setImage(new Image(PATH + name + ".png"));
                } else if (c instanceof Path) {
                    if (!(map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        for (int yChange = -1; yChange <= 1; yChange++) {
                            for (int xChange = -1; xChange <= 1; xChange++) {
                                if (map[x + xChange][y + yChange] instanceof Wall) {
                                    name += "0";
                                } else if (!(map[x + xChange][y + yChange] instanceof Wall)) {
                                    name += "1";
                                }
                            }
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        name += "000";
                        for (int yChange = 0; yChange <= 1; yChange++) {
                            for (int xChange = -1; xChange <= 1; xChange++) {
                                if (map[x + xChange][y + yChange] instanceof Wall) {
                                    name += "0";
                                } else if (!(map[x + xChange][y + yChange] instanceof Wall)) {
                                    name += "1";
                                }
                            }
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        for (int yChange = -1; yChange <= 1; yChange++) {
                            for (int xChange = -1; xChange <= 0; xChange++) {
                                if (map[x + xChange][y + yChange] instanceof Wall) {
                                    name += "0";
                                } else if (!(map[x + xChange][y + yChange] instanceof Wall)) {
                                    name += "1";
                                }
                            }
                            name += "0";
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        for (int yChange = -1; yChange <= 0; yChange++) {
                            for (int xChange = -1; xChange <= 1; xChange++) {
                                if (map[x + xChange][y + yChange] instanceof Wall) {
                                    name += "0";
                                } else if (!(map[x + xChange][y + yChange] instanceof Wall)) {
                                    name += "1";
                                }
                            }
                        }
                        name += "000";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        for (int yChange = -1; yChange <= 1; yChange++) {
                            name += "0";
                            for (int xChange = 0; xChange <= 1; xChange++) {
                                if (map[x + xChange][y + yChange] instanceof Wall) {
                                    name += "0";
                                } else if (!(map[x + xChange][y + yChange] instanceof Wall)) {
                                    name += "1";
                                }
                            }
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        name += "000111000";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        name += "010010010";
                        map[x][y].setImage(new Image(PATH + name + ".png"));

                    } else if (!(map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        name += "010010000";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        name += "000110000";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        name += "000011000";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        name += "000010010";
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        if (!(map[x - 1][y - 1] instanceof Wall)) {
                            name += "110110000";
                        } else {
                            name += "010110000";
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if (!(map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && (map[x][y + 1] instanceof Wall)) {
                        if (!(map[x + 1][y - 1] instanceof Wall)) {
                            name += "011011000";
                        } else {
                            name += "010011000";
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && (map[x - 1][y] instanceof Wall) &&
                        !(map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        if (!(map[x + 1][y + 1] instanceof Wall)) {
                            name += "000011011";
                        } else {
                            name += "000011010";
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    } else if ((map[x][y - 1] instanceof Wall) && !(map[x - 1][y] instanceof Wall) &&
                        (map[x + 1][y] instanceof Wall) && !(map[x][y + 1] instanceof Wall)) {
                        if (!(map[x - 1][y + 1] instanceof Wall)) {
                            name += "000110110";
                        } else {
                            name += "000110010";
                        }
                        map[x][y].setImage(new Image(PATH + name + ".png"));
                    }
                }
            }
        }
        return map;
    }
}