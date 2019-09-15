package quakeLogParser.classes;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private int id;
	private Map<String, Integer> playersKills;
	private int totalKills;

	public Game(int id) {
		setId(id);
		playersKills = new HashMap<String, Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalKills() {
		return totalKills;
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}

	public Map<String, Integer> getPlayersKills() {
		return playersKills;
	}

	public void setPlayersKills(Map<String, Integer> playersKills) {
		this.playersKills = playersKills;
	}

}
