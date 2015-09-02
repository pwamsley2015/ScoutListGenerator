import java.util.ArrayList;

/**
 * Data structure which represents a Conflict. <p>
 * 
 * Note: 49 / 50 times, a conflict will have 2 teams, though there could be up to 6, 
 * so thats why this takes an array of Teams. 
 * 
 * @author Patrick Wamsley
 */
public class Conflict {
	
	public final ArrayList<Team> teamsThatConflict; 
	public final Team team; 
	public final Match match;
	public final Scout scout; 

	public Conflict(Match match, Team team, ArrayList<Team> teams, Scout scout) {
		this.team 	= team; 
		this.match 	= match; 
		this.scout 	= scout;
		this.teamsThatConflict = teams;
	}

	//AUTO-GENERATED CODE
	@Override
	public String toString() {
		return "Conflict [teamsThatConflict=" + teamsThatConflict + ", team="
				+ team + ", match=" + match + ", scout=" + scout + "]";
	}
	
	
}

