import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Patrick Wamsley
 * @author Michael Maunu
 *
 * Generates lists containting teams for scouts
 */
public class ListGenerator {

	private int numScouts; 
	private File matchSchedFile; 

	private String csvString; 

	public ListGenerator(String file, int numScouts) {
		this.numScouts = numScouts; 
		matchSchedFile = new File(file); 

		try {
			loadString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public ListGenerator() {
		numScouts = Integer.parseInt(JOptionPane.
				showInputDialog("How many scouts do we have?")); 

		pickFile(); 

		try {
			loadString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @param optional arguements from command line: filePath numScouts
	 */
	public static void main(String[] args) {

		ListGenerator listGen; 

		//---if given params from command line---//
		if (args.length != 0) {
			try {
				listGen = new ListGenerator(args[0], Integer.parseInt(args[1])); 
			} catch (Exception e) {
				System.out.println("args not given correctly.");
				listGen = new ListGenerator(); 
			}
		} else 
			listGen = new ListGenerator(); 

		ArrayList<Match> matchSchedList = null; 

		try {
			matchSchedList = CsvParser.getMatchSchedList(listGen.getCsv());
		} catch (ParseException e) {
			e.printStackTrace();
		} 	
		//now we have an ArrayList which has all the matches in it

		ArrayList<ArrayList<Team>> lists = listGen.generateLists(listGen.getNumScouts(), matchSchedList);

		System.out.println(lists);

		//		int okayToPrint = JOptionPane.showConfirmDialog(null, 
		//				"Lists will now to try to print to your default printer."
		//						+ "\nIs this okay?"); 
		//
		//		if (okayToPrint == JOptionPane.YES_OPTION) {
		//			for (ArrayList<Team> list : lists) {
		//				ListCard cardToPrint = new ListCard(list); 
		//				cardToPrint.printCard();
		//			}
		//		} else {
		//			System.out.println("Was not allowed to print lists.");
		//		}
	}

	public ArrayList<ArrayList<Team>> generateLists(int numScouts, ArrayList<Match> matchSched) {

		ArrayList<ArrayList<Team>> lists = new ArrayList<ArrayList<Team>>(); 
		ArrayList<Team> teamList = new ArrayList<Team>(); 

		for (int i = 0; i < numScouts; i++) 
			lists.add(new ArrayList<Team>()); //now we just need to fill those lists

		/*
		 * 1) get team List (no repeats)
		 * 2) divide up power house teams
		 * 3) place all the other teams
		 * 4) test stregnth of that list
		 * 5) repeat until finds the best one
		 * 6) return best one
		 */

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

		for (int i = 0; i < teamList.size(); i++) {
			Team team = teamList.get(i); 
			if (team.isPowerHouseTeam()) 
				powerTeamPositions.add(i); 
		}
		if (powerTeamPositions.size() <= numScouts) 
			for (int i = 0; i < powerTeamPositions.size(); i++)
				lists.get(i).add(teamList.get(powerTeamPositions.get(i))); //add the first powerhouse team to the first list...
		else  { //more powerhouse teams than scouts or number of lists

			int numsTeamsPlaced = 0; 
			int currListPos = 0; 

			while (numsTeamsPlaced < powerTeamPositions.size()) {

				System.out.println(teamList.get(powerTeamPositions.get(numsTeamsPlaced)));
				lists.get(currListPos).add(teamList.get(powerTeamPositions.get(numsTeamsPlaced))); 

				numsTeamsPlaced++; 
				currListPos = this.nextPos(currListPos); 
			}
		}

		//---part three---//

		return lists; 
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
