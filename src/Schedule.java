import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a Schedule will document this later yay
 * 
 * @author Patrick Wamsley
 */
public class Schedule {

	private ArrayList<Match> matches;
	private HashSet<Team> teams;
	
	public Schedule(ArrayList<Match> matches) {
		this.matches = matches; 
		this.teams	 = new HashSet<>(); 
		
		/*
		 * We need to tell every team what matches they are in 
		 * so we can use it later to find conflicts.
		 * We're also using this to make a team list so that we have that. 
		 */
		for (Match match : matches) 
			for (Team team : match.getTeamsInMatch()) {
				team.addMatch(match); 
				teams.add(team); 
			}
	}
	
	public HashSet<Team> getAllTeams() {
		return teams; 
	}
	
	public ArrayList<Match> getMatches() {
		return matches; 
	}
	
	/**
	 * 
	 * Works by finding the matches each team is in, and checking if the team to add is in any of them.
	 * 
	 * @param teamToCheck to test
	 * @param scoutTeamList before the addition
	 * @return An {@code ArrayList} containing all the teams this team would conflict with. 
	 */
	public ArrayList<Team> getConflicts(Team teamToCheck, ArrayList<Team> scoutTeamList) {
		
		ArrayList<Team> conflictingTeams = new ArrayList<>(); 
		
		for (Team otherTeam : scoutTeamList)
			for (Match match : teamToCheck.getMatchesIn())
				if (match.getTeamsInMatch().contains(teamToCheck))
					conflictingTeams.add(otherTeam); 
		
		return conflictingTeams; 
		
	}
}
