import java.util.ArrayList;


/**
 * @author Patrick Wamsley
 * @author Michael Maunu
 *
 * This class represents a Team
 */
public class Team {

	private int teamNum; 
	private ArrayList<Match> matches; 

	private boolean isPowerHouseTeam; 

	private static int[] POWER_HOUSE_TEAMS = 
		{2485, 987, 254, 3476
		, 118, 1114, 2056, 33, 27 
		, 148, 67, 1986, 16 
		, 610, 1241, 469, 971
		, 1477, 359, 125, 1285
		, 368, 233, 188, 217, 71
		, 111, 1538, 1717, 25, 399
		, 51, 1983}; 

	public Team(int teamNum) {
		this.teamNum = teamNum; 
		assignPowerHouse(); 
	}

	@Override
	public String toString() {
		return "" + teamNum; 
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
