import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

/**
 * This class is used to print lists
 * @author Patrick Wamsley
 */
public class ListCard implements Printable {

	/**
	 * The list of teams for the scout to scout
	 */
	private ArrayList<Team> listCard; 

	public ListCard(ArrayList<Team> teams) {

		listCard = teams; 
	}

	/**
	 * Creates an image using {@code java.awt.Graphics} so the list can be printed. 
	 */
	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) 
			throws PrinterException {

		if (pageIndex > 0) 
			return NO_SUCH_PAGE;

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		int currY = 40; 

		for (Team currTeam : listCard) {
			if (currTeam.isPowerHouseTeam()) 
				g.setColor(Color.red);
			else
				g.setColor(Color.black);

			g.drawString(currTeam.toString(), 50, currY); 
			currY += 20; 
		}
		return PAGE_EXISTS;
	}
	
	/**
	 * Prints the list to the selected printer. 
	 */
	public void printCard() {

		PrinterJob job = PrinterJob.getPrinterJob();

		job.setPrintable(this);

		try {
			job.print();
		} catch (PrinterException ex) {
			System.out.println("Error printing the pages. Printing to console instead.");
			for (Team team : listCard)
				System.out.println(team.toString() + "\n");
			ex.printStackTrace();
		}
	}
}
