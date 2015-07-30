import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author Patrick Wamsley
 *
 * This class handles parsing the csv obtained from the blue aliance. 
 */
public class CSV_Parser {

	private CSV_Parser() {}
	
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
		} finally {
			scanner.close();
		}
		return CSV_String; 
	}
	
}
