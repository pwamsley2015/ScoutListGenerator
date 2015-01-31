import java.util.ArrayList;

/**
 * @author Patrick Wamsley
 *
 * This class represents a match, a game played in competition.
 * 
 * Each match object has a list of 6 teams that are playing in the match
 */
public class Match {

	private ArrayList<Team> teams; 

	private static int totalMatchNums; 
	private int matchNum; 
	
	public Match(ArrayList<Team> teams) {
		
		totalMatchNums++; 
		matchNum = totalMatchNums; 
		
		this.teams = teams; 
	}
	@Override 
	public String toString() {
		return "Match " + matchNum + ": " + teams.toString(); 
	}
	public ArrayList<Team> getTeamsInMatch() {
		return teams; 
	}
	
}
