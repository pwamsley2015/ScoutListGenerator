import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections; 

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * @author Patrick Wamsley
 *
 * Generates lists containting teams for scouts
 */
public class ListGenerator {

	private int numScouts; 
	private File matchSchedFile; 

	private String csvString; 

	private ArrayList<Scout> scouts; 

	public ListGenerator(String file, int numScouts) {
		this.numScouts = numScouts; 
		matchSchedFile = new File(file); 

		scouts = new ArrayList<Scout>(); 

		try {
			loadString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public ListGenerator() {

		scouts = new ArrayList<Scout>(); 

		numScouts = Integer.parseInt(JOptionPane.
				showInputDialog("How many scouts do we have?")); 

		pickFile(); 

		try {
			loadString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public ArrayList<ArrayList<Team>> generateLists(int numScouts, ArrayList<Match> matchSched, int... recursionCount) {

		System.out.println(recursionCount.length);
		
		ArrayList<ArrayList<Team>> lists = new ArrayList<ArrayList<Team>>(); 
		ArrayList<Team> teamList = new ArrayList<Team>(); 

		for (int i = 0; i < numScouts; i++) 
			lists.add(new ArrayList<Team>()); //now we just need to fill those lists

		//---part 1---// 
		for (Match match : matchSched) {
			ArrayList<Team> teamsInMatch = match.getTeamsInMatch(); 
			for (Team team : teamsInMatch)
				teamList.add(team); 
		}

		//get rid of duplicates
		Set<Team> teamSet = new HashSet<Team>(teamList); 
		teamList = new ArrayList<Team>(teamSet); 

		//---Give each team object a list of matches it is playing in---//
		for (Team team : teamList) {
			ArrayList<Match> matchesIn = new ArrayList<Match>(); 
			for (Match match : matchSched) 
				if (match.getTeamsInMatch().contains(team))
					matchesIn.add(match); 
			team.setMatches(matchesIn);
		}

		//---part two---//
		ArrayList<Integer> powerTeamPositions = new ArrayList<Integer>(); 
		int currListPos = 0;

		for (int i = 0; i < teamList.size(); i++) {
			Team team = teamList.get(i); 
			if (team.isPowerHouseTeam()) 
				powerTeamPositions.add(i); 
		}
		if (powerTeamPositions.size() <= numScouts) 
			for (; currListPos < powerTeamPositions.size(); currListPos++)
				lists.get(currListPos).add(teamList.get(
						powerTeamPositions.get(currListPos))); 

		else  { //more powerhouse teams than scouts or number of lists

			int numsTeamsPlaced = 0; 

			while (numsTeamsPlaced < powerTeamPositions.size()) {

				lists.get(currListPos).add(teamList.get(
						powerTeamPositions.get(numsTeamsPlaced))); 

				numsTeamsPlaced++; 
				currListPos = this.nextPos(currListPos); 
			}
		}

		//---part three---//

		//store this because we'll need it later
		ArrayList<Team> remainingTeams = new ArrayList<Team>(); 

		for (Team team : teamList) 
			if (!(team.isPowerHouseTeam()))
				remainingTeams.add(team); 

		for (Team team : remainingTeams) {
			lists.get(currListPos).add(team);
			currListPos = nextPos(currListPos); 
		}

		findConflicts(lists, matchSched);

		for (Scout scout : scouts) {
		
			if (scout.getNumConflicts() > 18) {
				
				Collections.shuffle(matchSched); 
				
//				if (recursionCount.length >= 100) {
//					System.out.println("after 100 tries, can't fix the lists.");
//					return lists; 
//				}
				
				lists = generateLists(numScouts, matchSched, new int[recursionCount.length + 1]); 
			}
		}
		return lists; 
	}
	public void findConflicts(ArrayList<ArrayList<Team>> lists,
			ArrayList<Match> matches) {

		for (int i = 0; i < numScouts; i++)
			scouts.add(new Scout(lists.get(i))); 

		for (Scout currScout : scouts) {

			ArrayList<Team> teamList = currScout.getTeamList(); 

			for (Match match : matches) {
				ArrayList<Team> teamsInMatch = match.getTeamsInMatch(); 

				int count = 0; 
				for (Team teamFromTeamList : teamList) {
					if (teamsInMatch.contains(teamFromTeamList)) {
						count++; 
						if (count > 1)
							teamFromTeamList.setCausesConflict(true); 
					}
				}
				if (count > 1)
					currScout.increaseNumConflicts(); 
			}
		}
	}
	private int nextPos(int n) {
		return ++n % numScouts;
	}

	public String getCsv() {
		return csvString; 
	}

	public int getNumScouts() {
		return numScouts;
	}

	private void loadString() throws IOException {

		StringBuilder fileContents = new StringBuilder((int)matchSchedFile.length()); 
		Scanner scanner = new Scanner(matchSchedFile); 
		String lineSeperator = System.getProperty("line.seperator"); 

		try {
			while (scanner.hasNextLine())
				fileContents.append(scanner.nextLine() + lineSeperator);
			csvString = fileContents.toString(); 
		} 
		finally {
			scanner.close();
		}
	}  

	private void pickFile() {

		JFileChooser chooser = new JFileChooser(); 

		int response = chooser.showOpenDialog(null);

		if (response == JFileChooser.APPROVE_OPTION) 
			matchSchedFile = chooser.getSelectedFile();
		else {
			JOptionPane.showMessageDialog(null, "You must pick a file to parse...");

			int tryAgain = JOptionPane.showConfirmDialog(chooser, "try again?"); 

			if (tryAgain == JOptionPane.YES_OPTION)
				pickFile(); //try again
			else {
				System.err.println("failed to load csvString, closing program.");
				System.exit(0); 
			}
		}
	}
}
