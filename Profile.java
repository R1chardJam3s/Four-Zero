import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for profiles in the game.
 * @author Hamish Tartaglia
 */
public class Profile {

    //String for username.
    private String userName;

    //ArrayList to store profiles.
    private static ArrayList <Profile> profiles = new ArrayList <> ();

    //ArrayList for levels.
    private ArrayList <Integer> levels = new ArrayList <> ();

    /**
     * Constructor for profiles.
     * @param userName username
     */
    public Profile(String userName) {
        this.userName = userName;
        this.levels.add(1);
    }

    /**
     * Setter for username.
     * @param userName new username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for username.
     * @return username profiles username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for levels.
     * @return levels arraylist of levels
     */
    public ArrayList<Integer> getLevels() {
        return levels;
    }

    /**
     * Getter for ArrayList of profiles.
     * @return profiles arraylist
     * @throws IOException Exception if profiles not found
     */
    public static ArrayList<Profile> getProfiles() throws IOException {
        profiles.clear();
        readProfiles();
        return profiles;
    }

    /**
     * Add level to level arraylist.
     * @param level level to be added
     * @throws IOException 
     */
    public void addLevel(Integer level) throws IOException {
        this.levels.add(level);
    }
    /**
     * Returns profile from a given name.
     * @param name profile name
     * @return Profile profile linked to given name
     */
    public static Profile getProfile(String name) {
        for (Profile p: profiles) {
            if (p.toString().equals(name)) {
                return p;
            }
        }
        return null;
    }
    /**
     * Writes the level to the profiles file.
     * @param level level to be added
     * @throws IOException 
     */
    public void addLevelToFile(int level) throws IOException {
        File inputFile = new File("profilesFile.txt");
        Scanner profileNames = new Scanner(inputFile);
        StringBuffer s = new StringBuffer();
        //Reads in file line by line
        while (profileNames.hasNextLine()) {
            String current = profileNames.nextLine();
            Scanner profile = new Scanner(current);
            profile.useDelimiter(",");
            String name = profile.next();
            //Looks if username is equal to the name on the current line
            if (name.equals(this.getUserName())) {
                s.append(this.getInfo() + level + "\n");
            } else {
                //Writes level to the end of the line
                s.append(current + "\n");
            }
            profile.close();
        }
        FileWriter fileOut = new FileWriter("profilesFile.txt");
        // Writes to file then closes file writer and scanner
        fileOut.write(s.toString());
        fileOut.close();
        profileNames.close();
        this.addLevel(level);
    }

    /**
     * Adds a profile to the ArrayList.
     * @param profile proflie being added
     * @throws IOException Exception if file not found
     */
    public static void addProfile(Profile profile) throws IOException {
        profiles.add(profile);
        File profileFile = new File("profilesFile.txt");
        FileWriter addProfile = new FileWriter(profileFile, true);
        addProfile.write(profile.getUserName() + ",1" + "\n");
        addProfile.close();
    }

    /**
     * Setter for levels.
     * @param levels levels possible to play
     */
    public void setLevels(ArrayList <Integer> levels) {
        this.levels = levels;
    }

    /**
     * Reads profiles.
     * @throws IOException if file not found
     */
    public static void readProfiles() throws IOException {
        File inputFile = new File("profilesFile.txt");
        Scanner profileNames = new Scanner(inputFile);
        while (profileNames.hasNextLine()) {
            Scanner profile = new Scanner(profileNames.nextLine());
            profile.useDelimiter(",");
            String name = profile.next();
            Profile addProf = new Profile(name);
            ArrayList <Integer> help = new ArrayList <> ();
            while (profile.hasNext()) {
                help.add(profile.nextInt());
            }
            profile.close();
            profiles.add(addProf);
            addProf.setLevels(help);
        }
        profileNames.close();
    }
    /**
     * Returns the profiles username and levels.
     * @return info profile username and levels
     */
    public String getInfo() {
        String info = "";
        info += this.getUserName() + ",";
        for (Integer i: this.getLevels()) {
            info += i + ",";
        }
        return info;
    }

    /**
     * Returns username of a profile.
     * @return userName username of profile
     */
    public String toString() {
        return this.userName;
    }
}