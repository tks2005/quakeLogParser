package quakeLogParser.classes;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private int id;
	private Map<String, Integer> playersKills;
	private Map<String, Integer> killsByMeans;
	private int totalKills;

	public Game(int id) {
		setId(id);
		playersKills = new HashMap<String, Integer>();
		killsByMeans = new HashMap<String, Integer>();
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

	public Map<String, Integer> getKillsByMeans() {
		return killsByMeans;
	}

	public void setKillsByMeans(Map<String, Integer> killsByMeans) {
		this.killsByMeans = killsByMeans;
	}

}
