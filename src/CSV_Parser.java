import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * This class conatins util methods to Parse data from a CSV.
 * 
 * @author Patrick Wamsley
 */
public class CSV_Parser {

	private CSV_Parser() {}

	/**
	 * Parses the file found at {@code filePathToCSV} to create the list of matches. 
	 * 
	 * @param String file path to the .csv schedule, uptained from thebluealiance.com
	 * @return List of Matches
	 */
	public static ArrayList<Match> getMatchSchedule(String filePathToCSV) { 

		String CSV_String; 

		try {
			CSV_String = getFileContents(filePathToCSV);
		} catch (FileNotFoundException e1) {
			CSV_String = ":("; 
			e1.printStackTrace();
		}

		ArrayList<String> lines = new ArrayList<>(); 
		ArrayList<Match> returnList = new ArrayList<>(); 

		//get an arraylist of lines, each containing: Qualn, t1, t2, t3, t4, t5, t6
		while (true) {
			try {
				String currLine = CSV_String.substring(0, CSV_String.indexOf("\n")); 
				lines.add(currLine); 
				CSV_String = CSV_String.substring(CSV_String.indexOf("\n") + 1); 
			}
			catch (Exception ex) {
				break; 
			}
		}

		int curr = 0; 
		while (true) {
			try {
				String lineWorker = lines.get(curr);
				lineWorker = lineWorker.substring(lineWorker.indexOf(',') + 1); 
				Team[] teams = new Team[6]; 
				for (int i = 0; i < 5; i++) {
					String teamNumString = lineWorker.substring(0, lineWorker.indexOf(',')); 
					teams[i] = new Team(Integer.parseInt(teamNumString)); 
					lineWorker = lineWorker.substring(lineWorker.indexOf(',') + 1); 
				}
				teams[5] = new Team(Integer.parseInt(lineWorker)); 
				returnList.add(new Match(curr + 1, teams)); 
			} catch(Exception e) {
				break; 
			}
			curr++; 
		}

		return returnList; 
	}

	/**
	 * Reads from the "PowerhouseTeams.csv" to create the list of powerhouse teams. <p> <p>
	 * Check the readme at {@link https://github.com/pwamsley2015/ScoutListGenerator/blob/master/README.md} for more details.
	 * 
	 * @return ArrayList of Integer containing team numers of power house teams. 
	 */
	public static ArrayList<Integer> loadPowerHouseTeams() {

		String rawCSV = "";
		try {
			rawCSV = getFileContents("PowerhouseTeams.csv");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
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
		return teamNumbers;
	}

	private static String getFileContents(String filePathtoCSV) 
			throws FileNotFoundException {

		File file = new File(filePathtoCSV); 
		String CSV_String; 

		StringBuilder fileContents = new StringBuilder((int)file.length()); 
		Scanner scanner = new Scanner(file); 
		String lineSeperator = "\n"; 

		try {
			while (scanner.hasNextLine())
				fileContents.append(scanner.nextLine() + lineSeperator);
			CSV_String = fileContents.toString(); 
			//remove the added "\n" 
			CSV_String = CSV_String.substring(0, CSV_String.length() - 1); 
		} finally {
			scanner.close();
		}
		return CSV_String; 
	}

}
