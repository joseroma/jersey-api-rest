package es.uma.ingenieria_web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.uma.ingenieria_web.model.Player;

public class DataService {
	
	private List<Player> players = new ArrayList<Player>();
	
	private static DataService instance = new DataService();
	
	private DataService() {
		System.out.println("INIT");
		players.addAll(Arrays.asList(
				new Player(1, "Leo Messi", "Delantero"),
				new Player(2, "Sergio Ramos", "Defensa"),
				new Player(3, "Gerard Pique", "Defensa"),
				new Player(4, "Karim Benzema", "Delantero"),
				new Player(5, "Diego Costa", "Delantero")
			));
	}
	
	public static DataService getInstance() {
		return instance;
	}
	
	public List<Player> getPlayers() {
		System.out.println("LENGTH:"+players.size());
		return players;
	}
	
	public void addPlayer(Player p) {
		System.out.println("adding "+p);
		players.add(p);
	}

}
