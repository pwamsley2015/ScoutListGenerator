import java.util.ArrayList;

/**
 * @author Patrick Wamsley
 *
 * Represents a Scout
 */

public class Scout {

	private ArrayList<Team> teamsToScout; 
	
	private static int numScouts; 
	private int id; 
	
	private static ArrayList<Team> teamsScouted = new ArrayList<>();
	
	public Scout() {
		
		teamsToScout = new ArrayList<>(); 
		
		numScouts++; 
		id = numScouts; 
	}
	
	public static boolean isScouted(Team t) {
		return teamsScouted.contains(t); 
	}
	
	public boolean addTeam(Team t) {
		
		if (teamsScouted.contains(t))
			return false; 
		
		System.out.println("Added a team to a scout list!");
		teamsScouted.add(t);
		return teamsToScout.add(t); 
	}
	
	public ArrayList<Team> getTeamList() {
		return teamsToScout; 
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Scout))
			return false; 
		return this.id == ((Scout)obj).id; 
	}
	
	@Override 
	public String toString() {
		return "Scout #" + id + 
				"\n Number of teams to Scout: "
				+ teamsToScout.size(); 
	}
}
