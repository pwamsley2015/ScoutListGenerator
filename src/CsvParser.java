import java.text.ParseException;
import java.util.ArrayList;


/**
 * @author Patrick Wamsley
 *
 * This class handles parsing csv's
 */
public class CsvParser {

	//---ensure that this is a static class---//
	private CsvParser() {}

	public static ArrayList<Match> getMatchSchedule(String csv) 
		throws ParseException { 

		ArrayList<String> lines = new ArrayList<>(); 
		ArrayList<Match> returnList = new ArrayList<>(); 
		
		//get an arraylist of lines, each containing: Qualn, t1, t2, t3, t4, t5, t6
		while (true) {
			try {
				String currLine = csv.substring(0, csv.indexOf("null")); 
				lines.add(currLine); 
				csv = csv.substring(csv.indexOf("null") + 4); 
			}
			catch (Exception ex) {
				break; 
			}
		}
		
		//first line is special case, deal with that first
		String firstLineWorker = lines.get(0).substring(8); //now we have something like: 4574,2193,3881,589,3749,2102
		Team[] firstGameTeams = new Team[6]; 
		for (int i = 0; i < 5; i++) {
			String teamNumString = firstLineWorker.substring(0, firstLineWorker.indexOf(',')); 
			firstGameTeams[i] = new Team(Integer.parseInt(teamNumString)); 
			firstLineWorker = firstLineWorker.substring(firstLineWorker.indexOf(',') + 1); 
		}
		firstGameTeams[5] = new Team(Integer.parseInt(firstLineWorker)); 
		returnList.add(new Match(1, firstGameTeams)); 
		
		//now do the rest of them... 
		int curr = 1; 
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
//				e.printStackTrace();
				break; 
			}
			curr++; 
		}
		
		if (returnList.size() == 0)
			throw new ParseException("failed to parse match sched csv.", -1);  
		
		return returnList; 
	}
}
