import java.util.Scanner;
import java.util.ArrayList;
import java.net.URL;

/**
 * This class contains functions that read in data from a website by using a screen scraper function
 * called LegendScraper. LegendScraper both scrapes and parses the data, using the information
 * to create NFLegend objects and adding each to an ArrayList<NFLegend>, which the function returns.
 *@author Bob Barr
 */
public class ScreenScraper {
	
	/**
	 * This function takes in a String representing a web address which is then used to create a new URL object.
	 * legendScraper() goes to the specified address, scans the source code into memory, and uses it to
	 * create NFLegend objects by parsing the data obtained, and assigning values to the relevant information
	 * pertaining to the NFL players listed in USA Today's Top 100 list.
	 * @param addr is the website url obtained from the user to direct which site legendScraper() will access
	 * @return ArrayList<NFLegend> legends is the array list of NFLegend objects created
	 */
	public static ArrayList<NFLegend> legendScraper(String addr) {
		
		ArrayList<NFLegend> legends = new ArrayList<NFLegend>();
		String rank, player, separator, position;
		String fname, mname, lname, careergap, singlecareer, fullcareer, line;
		String [] parts, careersplit;
		char S = 'S'; char J = 'J'; char r = 'r'; char period = '.';
		try {
			URL link = new URL(addr); // new URL object
			Scanner linksc = new Scanner(link.openStream());
			while (linksc.hasNextLine()) {
				line = linksc.nextLine();
				if ((line.length() > 0) && (line.contains("caption=")) && (!line.contains("3830775002"))) {
					ArrayList<String> career = new ArrayList<String>(); // ArrayList<String> reset to new for each line
					String s0 = line; // String containing the full slide
					String s1 = s0.substring(s0.indexOf("caption=")+9); // only portion of slide after "caption="
					String s2 = s1.split("\"")[0]; // only portion of slide before " (leaving only player details)
					parts = s2.split("\\s"); // split for use with rank, player, separator, & position
					rank = parts[0]; // player rank on the top100 list
					rank = rank.replaceAll(".$",""); // stripping off the period
					fname = parts[1];
					lname = parts[2];
					player = fname + " " + lname; //combining 2 strings to form full name, aka player
					separator = parts[3];
					position = parts[4]; // player position(s)
					position = position.replaceAll(",$", ""); //stripping off the comma
					if (!separator.contains("-")) { //if parts[3] does not contain the separator symbol
						mname = parts[2]; //that means the player name has 3 parts to it, so reassign accordingly
						lname = parts[3];
						separator = parts[4];
						position = parts[5];
						position = position.replaceAll(",$", "");
						player = fname + " " + mname + " " + lname;			
					}
					fullcareer = s2.substring(s2.indexOf(",")+2); //this strips off anything other than cities, teams, and years
					if (fullcareer.charAt(0) == S || fullcareer.charAt(0) == J) { //repositions start of fullcareer string if Sr. or Jr. is present
						if (fullcareer.charAt(1) == r) {
							if (fullcareer.charAt(2) == period) {
								fullcareer = fullcareer.substring(fullcareer.indexOf(",")+2);
							}
						}
					}
					fullcareer = fullcareer.replaceAll(".$",""); //strips the period off the end
					if (fullcareer.contains(", 1") || fullcareer.contains(", 2")) { //locates instances where the date is split, i.e. (1972-81, 1983)
						careergap = ""; //empty string used for combining the unique occurrence of date being split
						careersplit = fullcareer.split(",");  //String[] containing all Strings split at comma
						careergap = careersplit[0] + ", " + careersplit[1];  //combines String at [0] and [1] to reconnect the date
						career.add(careergap.trim());  //adds it to the ArrayList<String> career used to create NFLegend object
						if (careersplit.length > 2)  //cycles through and adds extra careers to ArrayList<String> career if they exist
							career.add(careersplit[2].trim());
						if (careersplit.length > 3) {
							career.add(careersplit[3].trim());
						}
						if (careersplit.length > 4) {
							career.add(careersplit[4].trim());
						}
						if (careersplit.length > 5) {
							career.add(careersplit[5].trim());
						}
						if (careersplit.length > 6) {
							career.add(careersplit[6].trim());
						} 
						
					} else {
							singlecareer = "";  //does essentially the same as above, but does not require combining a split date back together
							careersplit = fullcareer.split(",");
							career.add(careersplit[0].trim());
							if (careersplit.length > 1)
								career.add(careersplit[1].trim());
							if (careersplit.length > 2) {
								career.add(careersplit[2].trim());
							}
							if (careersplit.length > 3) {
								career.add(careersplit[3].trim());
							}
							if (careersplit.length > 4) {
								career.add(careersplit[4].trim());
							}
							if (careersplit.length > 5) {
								career.add(careersplit[5].trim());
							}
							if (careersplit.length > 6) {
								career.add(careersplit[6].trim());
							}
					}
				rank.trim(); player.trim(); position.trim();  //trims all white space off of constructors
				legends.add(new NFLegend(rank, player, position, career));  //uses constructors to create new NFLegend
				}
		}
		linksc.close();
		} catch (Exception ex) {
			System.out.println("You entered an incorrect file path. Please try again.");
			legends = null;	
		}
		// the following lines accommodate for an error in the source code (no rank was given to this player)
		ArrayList<String> errorfix = new ArrayList<String>();
		errorfix.add("Oakland Raiders (1998-2005)");
		errorfix.add("Green Bay Packers (2006-2012)");
		errorfix.add("Oakland Raiders (2013-2015)");
		legends.set(39, new NFLegend("61","Charles Woodson","CB",errorfix));
		// the following lines fix an irregularity in the website's formatting
		ArrayList<String> formatfix = new ArrayList<String>();
		formatfix.add("Los Angelas Rams (1952-53)");
		formatfix.add("Chicago Cardinals (1954-59)");
		formatfix.add("Detroit Lions (1960-65)");
		legends.set(67, new NFLegend("33", "Dick Lane", "DB",formatfix));
		
	return legends;  //returns a master ArrayList<NFLegend>
	}
}