import java.util.ArrayList;
import java.util.Scanner;

/**
 * The LegendMain class houses the main function for the program. The program
 * initializes with a new UserInterface object and is set to visible. From there, the program allows the 
 * user to enter in a URL. The 'Fetch' button allows for the source code to be obtained from the site via 
 * the ScreenScraper class, which is also responsible for parsing the data, assigning values to the 
 * NFLegend constructors, and creating/returning an ArrayList<NFLegend> objects. The objects are then 
 * returned to the center panel of the user interface in a nicely formatted string representation of 
 * themselves, and can from there be saved to a .txt or .json file by clicking on the appropriate button. 
 * @author BobBarr
 */
public class LegendMain {
	
	public static void main (String[] args) {
		
		UserInterface ui = new UserInterface();
		ui.setVisible(true);
	}
}
