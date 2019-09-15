package quakeLogParser.Utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import quakeLogParser.classes.Game;

public class ReporterUtil {
	
	
	//Método que retorna os detalhes das partidas
	public static void reportGames(List<Game> games) {

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

	//Relatório que elenca o ranking dos jogadores por partida pela ordem de kills
	public static void ranking(List<Game> games) {

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
	
	//Relátorio que mostra por partida a quantidade de cada tipo de morte ocorrida
	public static void reportKillByMeans(List<Game> games) {

		Iterator<Game> it = games.iterator();

		while (it.hasNext()) {
			Game game = it.next();

			SortedMap<Integer, String> map = new TreeMap<Integer, String>(Collections.reverseOrder());

			for (Map.Entry<String, Integer> pair : game.getKillsByMeans().entrySet()) {
				map.put(pair.getValue(), pair.getKey());
			}

			String id = "Game_" + game.getId();

			System.out.println("Tipos de morte do " + id);
			System.out.println("");

			int x = 1;
			for (Map.Entry<Integer, String> mapPair : map.entrySet()) {
				System.out.println(x + ". " + mapPair.getValue() + " = " + mapPair.getKey());
				x++;
			}
			System.out.println("================================");
			System.out.println("");

		}
	}

}
