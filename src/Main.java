import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Main {

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
			matchSchedList = CsvParser.getMatchSchedule(listGen.getCsv());
		} catch (ParseException e) {
			e.printStackTrace();
		} 	
		//now we have an ArrayList which has all the matches in it

		ArrayList<ArrayList<Team>> lists = listGen.generateLists(listGen.getNumScouts(), matchSchedList);
//		System.out.println(lists);

//		System.out.println("==========================================");
//		for (ArrayList<Team> list : lists)
//			for (Team t : list)
//				if (t.isConflict())
//					System.out.println(t);
//		
		int okayToPrint = JOptionPane.showConfirmDialog(null, 
				"Lists will now to try to print to your default printer."
						+ "\nIs this okay?"); 

		if (okayToPrint == JOptionPane.YES_OPTION) {
			for (ArrayList<Team> list : lists) {
				ListCard cardToPrint = new ListCard(list); 
				cardToPrint.printCard();
			}
		} else {
			System.out.println("Was not allowed to print lists, printing lists to console instead.\n\n\n");
			for (ArrayList list : lists) {
				System.out.println(list + "\n");
			}
		}

	}
}
