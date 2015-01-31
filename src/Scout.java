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
	
	private int numConflicts; 
	
	public Scout(ArrayList<Team> teamsToScout) {
		
		this.teamsToScout = teamsToScout;
		
		numScouts++; 
		id = numScouts; 
		
		numConflicts = 0; 
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

	public int getNumConflicts() {
		return numConflicts; 
	}
	
	public void increaseNumConflicts() {
		numConflicts++; 
	}
}
