import java.util.*;
import java.io.*;

/**
 * This class is used to hold the leaderboard information and display it.
 * @author Dom Smith
 */
public class Leaderboard {
	
	//Stores the data from the leaderboard files
    public ArrayList <Leaderboard> leaderList = new ArrayList <Leaderboard> ();
    //Arraylist of the highest scores
    private ArrayList <Integer> scoreList = new ArrayList <Integer> ();
    //Arraylist of the names with the highest scores
    private ArrayList <String> nameList = new ArrayList <String> ();
    //User name and the corresponding score
    private int score;
    private String userName;

    /**
     * Constructor for leaderboard class.
     * @param userName user name for the leaderboard
     * @param score score for the scoreboard
     */
    public Leaderboard(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    /**
     * Empty constructor for Leaderboard class that .
     */

    public Leaderboard() {
    	
    }

    /**
     * Sorts scores using the comparator class.
     */
    public void sortScores() {
        CompareScore comparator = new CompareScore();
        Collections.sort(leaderList, comparator);
    }

    /**
     * Loads in the score file for the specified level,
     * sorts them and adds them to different ArrayLists.
     * @param level level that the leaderboard is showing
     * @throws FileNotFoundException
     */
    public void loadScore(int level) throws FileNotFoundException {
        loadScoreFile(level);
        sortScores();
        addSorted();
    }
    
    /**
     * Uses the sorted leaderboard ArrayList and 
     * splits it into username and score ArrayLists.
     */
    public void addSorted() {
        for (int i = 0; i < leaderList.size(); i++) {
            String n = leaderList.get(i).getUserName();
            addName(n);
            int x = leaderList.get(i).getScore();
            addScore(x);
        }
    }

    /**
     * Method to add a player to the leaderboard.
     * @param userName username being added
     * @param score corresponding score being added
     */
    public void addPlayer(String userName, int score) {
        leaderList.add(new Leaderboard(userName, score));
    }
    
    /**
     * Method to add a score to the ArrayList of scores.
     * @param score the score being added
     */
    private void addScore(int score) {
        scoreList.add(score);
    }
    
    /**
     * Method to add a user name to the ArrayList of names.
     * @param userName username being added
     */
    private void addName(String userName) {
        nameList.add(userName);
    }

    /**
     * When a player finishes a level, it adds their user name and score
     * to the leaderboard file for the correct level.
     * @param userName user name being added
     * @param score score for the corresponding user name
     * @param level level that has just been completed
     * @throws FileNotFoundException
     */
    public void addPlayerProfile(String userName, int score, int level) throws FileNotFoundException {
        leaderList.add(new Leaderboard(userName, score));
        loadScoreFile(level);
        sortScores();
        addSorted();
    }

    /**
     * Loads the score file and adds each player to a leaderboard.
     * @param level the level the scores are being read from
     * @throws FileNotFoundException
     */
    private void loadScoreFile(int level) throws FileNotFoundException {
        File inputFile = new File("leaderboards/" + level + ".txt");
        Scanner in = new Scanner(inputFile);
        while (in.hasNext()) {
            String current = in.nextLine();
            Scanner line = new Scanner(current);
            line.useDelimiter(",");
            userName = line.next();
            score = line.nextInt();
            addPlayer(userName, score);
            line.close();
        } in.close();
        while (leaderList.size() < 3) {
            addPlayer("", 0);
        }
    }

    /**
     * Gets the highest score in the file.
     * @return scoreList index 0 of the scoreList ArrayList
     */
    public int getFileScore0() {
        return scoreList.get(0);
    }
    
    /**
     * Gets the highest score in the file.
     * @return scoreList index 1 of the scoreList ArrayList
     */
    public int getFileScore1() {
        return scoreList.get(1);
    }
    
    /**
     * Gets the highest score in the file.
     * @return scoreList index 2 of the scoreList ArrayList
     */
    public int getFileScore2() {
        return scoreList.get(2);
    }
    
    /**
     * Gets the name with the highest score in the file.
     * @return scoreList index 0 of the nameList ArrayList
     */
    public String getFileName0() {
        return nameList.get(0);
    }
    
    /**
     * Gets the name with the highest score in the file.
     * @return scoreList index 1 of the nameList ArrayList
     */
    public String getFileName1() {
        return nameList.get(1);
    }
    
    /**
     * Gets the name with the highest score in the file.
     * @return scoreList index 2 of the nameList ArrayList
     */
    public String getFileName2() {
        return nameList.get(2);
    }

    /**
     * Setter for the score
     * @return score the new score
     */
    private void setScore(int score) {
        this.score = score;
    }
    
    /**
     * Getter for the score.
     * @return score for this leaderboard
     */
    public int getScore() {
        return this.score;
    }
    
    /**
     * Getter for the username.
     * @return username for this leaderboard
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Setter for username.
     * @param userName new user name
     */
    private void setUserName(String userName) {
        this.userName = userName;
    }
}