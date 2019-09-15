package quakeLogParser.Main;

import java.io.BufferedReader;
import java.util.Scanner;

import quakeLogParser.Utils.LogReader;

public class RunParser {

	public static void main(String[] args) {


		Scanner sc = new Scanner(System.in);
		
		LogReader log = new LogReader();
		System.out.println("Por favor, digite o local do log:");
		String enderecoLog = sc.next();
		BufferedReader buffer = log.getBuffer(enderecoLog);
		
		log.ranking(log.readGames(buffer));
		
		sc.close();
	}

}
