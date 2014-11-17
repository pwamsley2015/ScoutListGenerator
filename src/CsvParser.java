import java.text.ParseException;
import java.util.ArrayList;


/**
 * @author Patrick Wamsley
 * @author Michael Maunu
 *
 * This class handles parsing comma seperated values 
 */
public class CsvParser {

	//---ensure that this is a static class---//
	private CsvParser() {}

	public static ArrayList<Match> getMatchSchedList(String matchSchedCsv)
			throws ParseException {

		matchSchedCsv = matchSchedCsv.substring(0,matchSchedCsv.length() - 4) + ","; //makes the while loop work correctly

		ArrayList<Match> returnList = new ArrayList<Match>(); 
		ArrayList<String> stringList = new ArrayList<String>(); 

		while (true) {
			try {
				int firstCommaPos = matchSchedCsv.indexOf(","); 
				String firstString = matchSchedCsv.substring(0, firstCommaPos); 

				stringList.add(firstString);
				matchSchedCsv = matchSchedCsv.substring(firstCommaPos + 1); 
			}
			catch (Exception e) {
				break; 
			}
		}
//		System.out.println(stringList);
		/* 
		 * now we have an arrayList of all those strings
		 * something like:
		 * 
		 * [qual1,2485,254,987,3476,1538,1114,qual2...] 
		 */

		//---fill returnList with match Objects---// 
		while (true) {
			try {
				ArrayList<Team> teamsInMatch = new ArrayList<Team>(); 

				for (int pos = 1; teamsInMatch.size() != 6; pos++) 
					teamsInMatch.add(new Team(Integer.parseInt(stringList.get(pos)))); 
				returnList.add(new Match(teamsInMatch)); 

				for (int i = 0; i <= 6; i++) 
					stringList.remove(0); 
			}
			catch (Exception e) {
				break;
			}
		}
		if (returnList.size() == 0)
			throw new ParseException("Failed to parse the csv String. *program explodes*", -1); 	
		return returnList; 
	}
}
