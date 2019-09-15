package quakeLogParser.Main;

import java.io.BufferedReader;
import java.util.Scanner;

import quakeLogParser.Utils.LogReader;
import quakeLogParser.Utils.ReporterUtil;

public class RunParser {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LogReader log = new LogReader();

		System.out.println("Por favor, digite o local do log para iniciar ou 0 para encerrar a aplicação:");

		String locationLog = sc.next();

		String opcao = "";

		while (!opcao.equals("0")) {

			BufferedReader buffer = log.getBuffer(locationLog);

			System.out.println("Escolha o número da opção desejada:");
			System.out.println("1. Detalhes dos jogos");
			System.out.println("2. Ranking de jogadores por jogo");
			System.out.println("3. Tipos de mortes por jogo");
			System.out.println("0. Sair");

			opcao = sc.next();

			if (opcao.equals("1"))
				ReporterUtil.reportGames(log.readGames(buffer));
			else if (opcao.equals("2"))
				ReporterUtil.ranking(log.readGames(buffer));
			else if (opcao.equals("3"))
				ReporterUtil.reportKillByMeans(log.readGames(buffer));
			else if (opcao.equals("0"))
				ReporterUtil.reportKillByMeans(log.readGames(buffer));
			else
				System.out.println("Por favor, selecione uma opção valida");

		}
		sc.close();
	}

}
