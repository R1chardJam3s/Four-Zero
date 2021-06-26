import java.util.Comparator;

/**
 * This class is used to compare two different scores.
 * @author Dom Smith
 */

public class CompareScore implements Comparator <Leaderboard> {

	/**
	 * Compares two scores for the leaderboards.
	 * @param score1 the score being compared to score2
	 * @param score2 the score being compared to score1
	 * @return int -1 if score1 > score2; 1 if score1 < score2; 0 otherwise
	 */
    public int compare(Leaderboard score1, Leaderboard score2) {
        int scoreA = score1.getScore();
        int scoreB = score2.getScore();
        if (scoreA > scoreB) {
            return -1;
        } else if (scoreA < scoreB) {
            return 1;
        } else {
            return 0;
        }
    }
}