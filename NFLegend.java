import java.io.Serializable;
import java.util.ArrayList;


/**
 *This class serves as the model class for this program, and it represents a current or former
 *NFL player that has been ranked by USA Today in their top 100 all-time players list. 
 *It takes in multiple data members pertaining to the listed player, including...
 *the player's rank on the list, their name, the position they played, the team(s) they played
 *for, and the years the player was on said team(s).
 *@author Bob Barr
 */
public class NFLegend {
	
	private String rank;
	private String player;
	private String position;
	private ArrayList<String> career;
	
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public ArrayList<String> getCareer() {
		return career;
	}
	public void setCareer(ArrayList<String> career) {
		this.career = career;
	}
	
	public NFLegend(String rank, String player, String position, ArrayList<String> career) {
		setRank(rank); setPlayer(player); setPosition(position);
		setCareer(career);
	}
	
	public NFLegend() {
		rank = ""; player = ""; position = "";
		career = null;
	}
	
	
	public String toString() {
		String careerprint = "";
		for (String car : career) {
			careerprint = careerprint + car + "\n";
		}
		return String.format("\nRank: # %s\n"
				+"%s --- %s\n"
				+"%s"
				+"------------------------------------ \n", rank, player, position, careerprint);
	}
}