
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ListGenerator {

	/**
	 * @param optional arguements from command line: filePath numScouts
	 */
	public static void main(String[] args) {

		if (args.length != 2)
			throw new IllegalArgumentException("Did not call this program correctly. \n "
					+ "Command Line all must be as follows: java -j ListGenerator.jar filePathToCSV numScouts \n"); 
		
		/*
		 * First, fill up an instance of Schedule
		 */
		final Schedule schedule = new Schedule(CSV_Parser.getMatchSchedule(args[0])); 
		
		/*
		 * Then geneate the list. 
		 */
		ArrayList<Scout> scoutList = generateLists(Integer.parseInt(args[1]), schedule);
	
		/*
		 * Then ask if its okay to print to the default printer.
		 */
		int okayToPrint = JOptionPane.showConfirmDialog(null, 
				"Lists will now to try to print to your default printer."
						+ "\nIs this okay?"); 
		/*
		 * If yes, print. Otherwise we will just print to the console. 
		 */
		if (okayToPrint == JOptionPane.YES_OPTION) {
//			for (ArrayList<Team> list : lists) {
//				ListCard cardToPrint = new ListCard(list); 
//				cardToPrint.printCard();
//			}
		} else {
			System.out.println("\nWas not allowed to print lists, printing lists to console instead.\n\n\n");
			for (Scout list : scoutList) 
				System.out.println(list.getTeamList() + "\n");
		}

	}
	
	private static ArrayList<Scout> generateLists(int numScouts, Schedule schedule) {
		
		/*
		 * First, create the list of scouts objects.
		 * Once we have this list, we can start assigning teams to Scouts.
		 */
		ArrayList<Scout> scoutList = createScoutList(numScouts); 
		
		/*
		 * Before the other teams, distrute the "powerhouse" teams. 
		 * This way all scouts will have a roughly equal fun-to-scout schedule. 
		 */
		int currScoutIndex = 0; //this variable keeps track of we where we are in the scoutList when we are adding teams in a loop. 
		
		for (Team potenialPowerhouseTeam : schedule.getAllTeams()) { //working in sets, so no worries about duplicates. 
			if (potenialPowerhouseTeam.isPowerHouseTeam()) {
				scoutList.get(currScoutIndex).addTeam(potenialPowerhouseTeam); 
				currScoutIndex = nextScoutPos(currScoutIndex, numScouts); 
			}
		}
		
		System.out.println("Placed powerhouse teams!");
		
		/*
		 * Now we begin adding the rest of the teams to each scout. 
		 * 
		 * In this first pass, we either give a scout a team,
		 * or we add it to the conflict pool,
		 * where we will deal with it later. 
		 */
		ArrayList<Conflict> conflictPool = new ArrayList<>(); 
			
		for (Match currMatch : schedule.getMatches()) {
			for (Team currTeam : currMatch.getTeamsInMatch()) {
				ArrayList<Team> conflicts = schedule.getConflicts(currTeam, scoutList.get(currScoutIndex).getTeamList());
				if (Scout.isScouted(currTeam)) //if we've already taken care of this team, skip it.
					continue; 
				else if (conflicts.size() != 0) //else if (there are conflicts) ...
					conflictPool.add(new Conflict(currMatch, currTeam, conflicts, scoutList.get(currScoutIndex))); //add to the conflictPool. We'll deal with them later.
				else {
					scoutList.get(currScoutIndex).addTeam(currTeam); //Othersize, we assign this Team to a scout. 
					currScoutIndex = nextScoutPos(currScoutIndex, numScouts); 
				}
			}
		}
		
		System.out.println("Placed the first round of other teams!");
		
		/*
		 * At this point, all of the teams are either
		 * assigned to a scout, or kept in the conflits pool. 
		 * Now we work on placing each conflit pool team. 
		 */		
		boolean makingProgress = true; //true as long as conflictPool is getting smaller 
		
		while (makingProgress) {
			int sizeBefore = conflictPool.size(); 
			for (int i = 0; i < conflictPool.size(); i++) {
				
			Conflict conflict = conflictPool.get(i); 
			Team currentTeam = conflict.team; 
			
			 for (currScoutIndex = 0; currScoutIndex < numScouts; currScoutIndex++) {
				 
				 if (schedule.getConflicts(currentTeam, scoutList.get(currScoutIndex).getTeamList()).size() == 0) {
					 scoutList.get(currScoutIndex).addTeam(currentTeam); 
					 System.out.println("Prevented a conflict!");
					 conflictPool.remove(i); 
					 i--; 
					 break; 
				 }
			 }
			}  makingProgress = conflictPool.size() < sizeBefore; 
		} 
		
		System.out.println("About to place the last few teams.");

		/*
		 * Now we just need to assign the rest of the teams, 
		 * which unfortunitely we can't avoid conflicts with.
		 * 
		 * We can use this to try and even out the lists so they have
		 * about the same number of teams / scout list. 
		 */
		for (Conflict c : conflictPool) {
			currScoutIndex = findShortestList(scoutList); 
			scoutList.get(currScoutIndex).addTeam(c.team); 
		}
		
		System.out.println("Could not place " + conflictPool.size() + " teams.");
		
		return scoutList; 
	}
	
	private static int findShortestList(ArrayList<Scout> lists) {
		int shortest = lists.get(0).getTeamList().size(); 
		for (int i = 1; i < lists.size(); i++) 
			if (lists.get(i).getTeamList().size() < shortest)
				shortest = lists.get(i).getTeamList().size(); 
		return shortest; 
	}
	
	private static ArrayList<Scout> copyList(ArrayList<Scout> toClone) {
		ArrayList<Scout> returnList = new ArrayList<>(); 
		for (Scout s : toClone)
			returnList.add(s); 
		return returnList; 
	}
	
	private static int nextScoutPos(int currPos, int numScouts) {
		return ++currPos % numScouts; 
	}
	
	private static ArrayList<Scout> createScoutList(int numScouts) {
		
		ArrayList<Scout> scouts = new ArrayList<>(); 
		
		for (int i = 0; i < numScouts; i++) 
			scouts.add(new Scout()); 
		
		return scouts; 
	}
	
}
