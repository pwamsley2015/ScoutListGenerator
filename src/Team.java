import java.util.ArrayList;
import java.util.HashSet;


/**
 * @author Patrick Wamsley
 *
 * This class represents a Team
 */
public class Team {

	private int teamNum; 
	private ArrayList<Match> matches; 

	private boolean isPowerHouseTeam; 
	
	private boolean causesConflict; 

	private static int[] POWER_HOUSE_TEAMS = 
		{2485, 987, 254, 3476
		, 118, 1114, 2056, 33, 27 
		, 148, 67, 1986, 16 
		, 610, 1241, 469, 971
		, 1477, 359, 125, 1285
		, 368, 233, 188, 217, 71
		, 111, 1538, 1717, 25, 399
		, 51, 1983, 1678, 330, 294}; 

	public Team(int teamNum) {
		this.teamNum = teamNum; 
		assignPowerHouse(); 
		
		causesConflict = false; 
	}
	
	public boolean isConflict() {
		return causesConflict; 
	}
	
	public void setCausesConflict(boolean b) {
		causesConflict = b; 
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

	public void setMatches(ArrayList<Match> matches) {
		if (matches == null)
			throw new NullPointerException(
					"Tried to assign a null list of matches to team: "
							+ this.teamNum); 
		this.matches = new ArrayList<Match>(new HashSet<Match>(matches)); //make sure no duplicates
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
}
