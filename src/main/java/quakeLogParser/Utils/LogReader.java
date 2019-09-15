package quakeLogParser.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import quakeLogParser.classes.Game;

public class LogReader {

	public BufferedReader getBuffer(String file) {

		BufferedReader buffer = null;

		try {
			FileReader fileReader = new FileReader(file);
			buffer = new BufferedReader(fileReader);

		} catch (FileNotFoundException e) {
			System.err.printf("Erro ao abrir arquivo");
			e.printStackTrace();
		}

		return buffer;

	}

	public List<Game> readGames(BufferedReader buffer) {

		List<Game> games = new ArrayList<Game>();

		String linha;

		try {
			linha = buffer.readLine();

			Game game = null;
			Map<String, Integer> playersKills = new HashMap<String, Integer>();

			boolean activeGame = false;
			int idGame = 0;
			int totalGameKill = 0;

			while (linha != null) {

				if (activeGame) {

					if (linha.contains("InitGame:")) {
						game.setTotalKills(totalGameKill);
						game.setPlayersKills(playersKills);
						games.add(game);
						playersKills = new HashMap<String, Integer>();
						totalGameKill = 0;
						game = new Game(idGame);
						idGame++;
						activeGame = true;
					}

					if (linha.contains("ShutdownGame:")) {
						game.setTotalKills(totalGameKill);
						game.setPlayersKills(playersKills);
						games.add(game);
						playersKills = new HashMap<String, Integer>();
						totalGameKill = 0;
						activeGame = false;

					} else {

						if (linha.contains("Kill")) {
							totalGameKill++;

							String[] split = linha.split(":");

							String linhaPlayer = split[3];
							int indEndNamePlayer = linhaPlayer.indexOf("killed");
							linhaPlayer = linhaPlayer.substring(1, indEndNamePlayer - 1);

							int qtdeKills;

							if (linhaPlayer.equals("<world>")) {
								String[] newSplit = linha.split("killed");
								String worldKilled = newSplit[1];
								int indEndPlayerKilled = worldKilled.indexOf("by");
								String playerKilled = worldKilled.substring(1, indEndPlayerKilled - 1);

								if (playersKills.containsKey(playerKilled)) {
									qtdeKills = playersKills.get(playerKilled);
									playersKills.put(playerKilled, qtdeKills - 1);
								} else
									playersKills.put(playerKilled, -1);

							} else {
								if (playersKills.containsKey(linhaPlayer)) {
									qtdeKills = playersKills.get(linhaPlayer);
									playersKills.put(linhaPlayer, qtdeKills + 1);
								} else
									playersKills.put(linhaPlayer, 1);

							}
						}
					}

				} else {
					if (linha.contains("InitGame:")) {
						game = new Game(idGame);
						idGame++;
						activeGame = true;
					}
				}

				linha = buffer.readLine(); // lê da segunda até a última linha
			}
		} catch (IOException e) {
			System.err.print("Erro ao ler arquivo de log");
			e.printStackTrace();
		}

		return games;

	}

	public void reportGames(List<Game> games) {

		Iterator<Game> it = games.iterator();

		while (it.hasNext()) {
			Game game = it.next();

			String id = "Game_" + game.getId();

			System.out.println(id + ": ");
			System.out.println("total kills:" + game.getTotalKills());
			System.out.print("player: [");
			for (Map.Entry<String, Integer> pair : game.getPlayersKills().entrySet()) {
				System.out.print(pair.getKey() + ", ");
			}
			System.out.println("]");

			System.out.println("kills: ");

			for (Map.Entry<String, Integer> pair : game.getPlayersKills().entrySet()) {
				System.out.print(pair.getKey() + ": ");
				System.out.print(pair.getValue() + ",");
				System.out.println("");
			}
			System.out.println("======================");

		}
	}

	public void ranking(List<Game> games) {

		Iterator<Game> it = games.iterator();

		while (it.hasNext()) {
			Game game = it.next();

			SortedMap<Integer, String> map = new TreeMap<Integer, String>(Collections.reverseOrder());

			for (Map.Entry<String, Integer> pair : game.getPlayersKills().entrySet()) {
				map.put(pair.getValue(), pair.getKey());
			}
			
			String id = "Game_" + game.getId();

			System.out.println("Ranking do " + id);
			System.out.println("");

			int x = 1;
			for (Map.Entry<Integer, String> mapPair : map.entrySet()) {
				System.out.println(x + ". " + mapPair.getValue());
				x++;
			}
			System.out.println("================================");
			System.out.println("");

		}
	}

}
