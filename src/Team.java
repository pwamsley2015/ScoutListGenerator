import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * @author Patrick Wamsley
 *
 * This class represents a Team
 */
public class Team {

	private int teamNum; 
	private ArrayList<Match> matches; 

	private boolean isPowerHouseTeam; 

	private static ArrayList<Integer> POWER_HOUSE_TEAMS = loadPowerHouseTeams(); 

	public Team(int teamNum) {
		this.teamNum = teamNum; 
		assignPowerHouse(); 
		matches = new ArrayList<Match>(); 
	}
	
	private static ArrayList<Integer> loadPowerHouseTeams() {
		
		File file = new File("PowerhouseTeams.2485file"); 
		String rawCSV = ""; 
		
		StringBuilder fileContents = new StringBuilder((int)file.length()); 
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		String lineSeperator = "\n"; 

		try {
			while (scanner.hasNextLine()) 
				fileContents.append(scanner.nextLine() + lineSeperator);
			rawCSV = fileContents.toString(); 
		} finally {
			scanner.close();
		}
		
		ArrayList<Integer> teamNumbers = new ArrayList<>(); 
		
		while (true) {
			try {
				String firstNum = rawCSV.substring(0, rawCSV.indexOf(',')); //get the number
				teamNumbers.add(Integer.parseInt(firstNum)); //add the team
				rawCSV = rawCSV.substring(rawCSV.indexOf(',') + 1); //remove that number from the list
			} catch (IndexOutOfBoundsException e) {
				teamNumbers.add(Integer.parseInt(rawCSV)); //need to get the last team
				break; 
			}
		}
		
		System.out.println(teamNumbers);
		
		return teamNumbers;
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
}
