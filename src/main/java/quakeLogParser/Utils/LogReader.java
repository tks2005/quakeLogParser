package quakeLogParser.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quakeLogParser.classes.Game;

public class LogReader {

	//Carrega o BufferedReader de arquivo com o arquivo de logs carregados
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

	//Método responsável por realizar o parse do log e retornar uma lista de objetos do tipo Game que terá as informações de cada partida
	public List<Game> readGames(BufferedReader buffer) {

		List<Game> games = new ArrayList<Game>();

		String linha;

		try {
			linha = buffer.readLine();

			Game game = null;
			Map<String, Integer> playersKills = new HashMap<String, Integer>();
			Map<String, Integer> killMeans = new HashMap<String, Integer>();

			boolean activeGame = false;
			int idGame = 0;
			int totalGameKill = 0;

			while (linha != null) {

				if (activeGame) {

					if (linha.contains("InitGame:")) {
						game.setTotalKills(totalGameKill);
						game.setPlayersKills(playersKills);
						game.setKillsByMeans(killMeans);
						games.add(game);
						playersKills = new HashMap<String, Integer>();
						killMeans = new HashMap<String, Integer>();

						totalGameKill = 0;
						game = new Game(idGame);
						idGame++;
						activeGame = true;
					}

					if (linha.contains("ShutdownGame:")) {
						game.setTotalKills(totalGameKill);
						game.setPlayersKills(playersKills);
						game.setKillsByMeans(killMeans);
						games.add(game);
						playersKills = new HashMap<String, Integer>();
						killMeans = new HashMap<String, Integer>();
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

							String mean = linha.substring(linha.indexOf("MOD_"));
							if (killMeans.containsKey(mean)) {
								int kills = killMeans.get(mean);
								killMeans.put(mean, kills + 1);
							} else
								killMeans.put(mean, 1);
						}
					}

				} else {
					if (linha.contains("InitGame:")) {
						game = new Game(idGame);
						idGame++;
						activeGame = true;
					}
				}

				linha = buffer.readLine();
			}
		} catch (IOException e) {
			System.err.print("Erro ao ler arquivo de log");
			e.printStackTrace();
		}

		return games;

	}

}
