import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a match, with a match number and a list of teams playing. 
 * 
 * @author Patrick Wamsley
 */
public class Match {

	private ArrayList<Team> teamsInMatch; 
	public final int matchNum; 
	
	public Match(int matchNum, ArrayList<Team> teamsInMatch) {
		if (teamsInMatch.size() != 6)
			throw new IllegalArgumentException("When constructing a match object, you need to give it 6 teams."); 
		
		this.teamsInMatch = teamsInMatch; 
		this.matchNum = matchNum; 
	}
	
	//in case you want to construct one with 6 cs Teams rather than an arraylist
	public Match(int matchNum, Team... teams) {
		this(matchNum, new ArrayList<Team>(Arrays.asList(teams))); 
	}
	
	@Override
	public int hashCode() {
		return matchNum; //hack so we can use sets later and not have duplicates
	}
	
	@Override 
	public String toString() {
		return "Match " + matchNum + ": " + teamsInMatch.toString();
	}
	
	public ArrayList<Team> getTeamsInMatch() {
		return teamsInMatch; 
	}

	public int getMatchNum() {
		return matchNum;
	}
}
