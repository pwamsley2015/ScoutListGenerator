import java.util.ArrayList;

/**
 * @author Patrick Wamsley
 *
 * Represents a Team
 */
public class Team {

	private int teamNum; 
	private ArrayList<Match> matches; 

	private boolean isPowerHouseTeam; 

	private static ArrayList<Integer> POWER_HOUSE_TEAMS; 

	public Team(int teamNum) {
		this.teamNum = teamNum; 
		assignPowerHouse(); 
		matches = new ArrayList<Match>(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Team))
			return false; 
		return ((Team)obj).teamNum == this.teamNum; 
	}
	@Override
	public int hashCode() {
		return teamNum; 
	}
	@Override
	public String toString() {
		return "" + teamNum;
	}

	public boolean addMatch(Match matchIn) {
		return matches.add(matchIn); 
	}
	
	public ArrayList<Match> getMatchesIn() {
		if (matches == null)
			throw new NullPointerException("match list has not been initilized yet");
		return matches; 
	}
	private void assignPowerHouse() { 

		boolean inList = false; 

		for (int powerTeamNum : POWER_HOUSE_TEAMS) {
			if (this.teamNum == powerTeamNum) {
				inList = true; 
				break; 
			}
		}
		isPowerHouseTeam = inList; 
	}
	
	public boolean isPowerHouseTeam() {
		return isPowerHouseTeam; 
	}
	
	public static void setPowerHouseTeam(ArrayList<Integer> nums) {
		POWER_HOUSE_TEAMS = nums; 
	}
}
