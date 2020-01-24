import java.util.ArrayList;
import org.json.simple.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

/**
 * This class contains functions that write to screen, text, and JSON files
 * @author Bob Barr
 */
public class LegendWriter {
	
	/**
	 * This function iterates through an ArrayList<NFLegend> and uses the object's
	 * toString() function to print each in a nicely formatted manner to the screen
	 * @param legends represents the ArrayList<NFLegend> passed into the function
	 */
	public static void writeLegendsToScreen(ArrayList<NFLegend> legends) {
		for (NFLegend legend : legends) {
			System.out.println(legend);
		}
	}
	
	/**
	 * This function writes the objects loaded into memory, i.e. the ArrayList<NFLegend> legends,
	 * to a tab-delimited text file with a user-specified file name/path.
	 * @param fname File representing the file name/path
	 * @param legends ArrayList<NFLegend> representing the current contents of ArrayList<NFLegend> legends
	 * @return boolean true if writing to .txt was successful, false if not
	 */
	public static boolean writeLegendsToTextFile(File fname,
			ArrayList<NFLegend> legends) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new FileWriter((fname))));
			// iterates through all NFLegend objects and prints each, one per line
			for (NFLegend legend : legends) {
				ArrayList<String> career = legend.getCareer();
				String singleLineCareer = "";
				for (String car : career) {
					singleLineCareer = singleLineCareer + " " + car;
				}
			pw.printf("%s	%s	%s	%s\n", legend.getRank(),
			legend.getPlayer(),legend.getPosition(),singleLineCareer);
			}
			pw.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * This function writes the objects loaded into memory, i.e. the ArrayList<NFLegend> legends,
	 * to a JSON file with a user-specified file name/path.
	 * To more easily serialize the NFLegend objects to JSON, org.json.simple* is imported. 
	 * @param fname File representing the file name/path
	 * @param legends ArrayList<NFLegend> representing all of the NFLegend objects
	 * @return boolean true if writing to JSON was successful, false if not
	 */
	public static boolean writeLegendsToJSON(File fname, ArrayList<NFLegend> legends) {
		try {
			// BufferedWriter is used because JSON files can be very large
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fname)));
			// create a JSON object for each
			JSONObject legendObj;
			JSONArray array = new JSONArray();
			// iterates through the ArrayList<NFLegend>
			// creates a JSON object that is essentially a replication of the NFLegend object, 
			// and then adds it to the JSON array
			for (NFLegend legend : legends) {
				legendObj = new JSONObject();
				legendObj.put("rank", legend.getRank());
				legendObj.put("player", legend.getPlayer());
				legendObj.put("position", legend.getPosition());
				legendObj.put("career", legend.getCareer());
				array.add(legendObj);
			}
			// this is the outer JSONObject containing one single key "legends"
			// whose value is the entire list of legendObj
			// printing this object using the toJSONString() creates the contents
			// of the JSON file that is saved
			JSONObject outer = new JSONObject();
			outer.put("legends", array);
			pw.println(outer.toJSONString());
			pw.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}