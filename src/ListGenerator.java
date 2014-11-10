import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

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
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public ListGenerator() {
		numScouts = Integer.parseInt(JOptionPane.
				showInputDialog("How many scouts do we have?")); 

		pickFile(); 

		try {
			loadString();
		} 
		catch (IOException e) {
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
			}
			catch (Exception e) {
				System.out.println("args not given correctly.");
				listGen = new ListGenerator(); 
			}
		}
		else 
			listGen = new ListGenerator(); 
		
		ArrayList<Match> matchSchedList = null; 

		try {
			 matchSchedList = CsvParser.getMatchSchedList(listGen.getCsv());
		} 
		catch (ParseException e) {
			e.printStackTrace();
		} 	
		//now we have an ArrayList which has all the matches in it
		
		
	}

	public String getCsv() {
		return csvString; 
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
