package uk.co.unclealex.rokta.pub.views;

import java.io.Serializable;
import java.util.List;

public class InitialPlayers implements Serializable {

	private List<String> i_allBarExemptPlayers;
	private List<String> i_allUsers;
	private String i_exemptPlayer;
	
	protected InitialPlayers() {
		// Default constructor for serialisation
	}
	
	public InitialPlayers(List<String> allUsers, List<String> allBarExemptPlayers, String exemptPlayer) {
		super();
		i_allUsers = allUsers;
		i_allBarExemptPlayers = allBarExemptPlayers;
		i_exemptPlayer = exemptPlayer;
	}

	public List<String> getAllBarExemptPlayers() {
		return i_allBarExemptPlayers;
	}
	
	public String getExemptPlayer() {
		return i_exemptPlayer;
	}
	
	public List<String> getAllUsers() {
		return i_allUsers;
	}
}
