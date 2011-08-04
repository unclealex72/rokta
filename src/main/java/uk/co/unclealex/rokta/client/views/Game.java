package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Game implements Serializable {

	private List<String> i_allBarExemptPlayers;
	private List<String> i_allUsers;
	private String i_exemptPlayer;
	private String i_instigator;
	
	private Date i_datePlayed;
	
	private List<String> i_players;
	private State i_gameState;
	private List<Map<String, Hand>> i_rounds;
	private List<String> i_counters;
	
	public Game() {
		// Default empty constructor for serialisation.
	}
	
	public void initialise(List<String> allUsers, List<String> allBarExemptPlayers, String exemptPlayer) {
		setAllUsers(allUsers);
		setAllBarExemptPlayers(allBarExemptPlayers);
		setExemptPlayer(exemptPlayer);
		setGameState(State.CHOOSE_PLAYERS);
		setRounds(new ArrayList<Map<String,Hand>>());
		setPlayers(new ArrayList<String>());
		setCounters(new ArrayList<String>());
	}

	public enum State {
		CHOOSE_PLAYERS, PLAYING, FINISHED, SUBMITTING, SUBMITTED
	}

	public List<String> getAllBarExemptPlayers() {
		return i_allBarExemptPlayers;
	}
	
	protected void setAllBarExemptPlayers(List<String> allBarExemptPlayers) {
		i_allBarExemptPlayers = allBarExemptPlayers;
	}
	
	public String getExemptPlayer() {
		return i_exemptPlayer;
	}
	
	protected void setExemptPlayer(String exemptPlayer) {
		i_exemptPlayer = exemptPlayer;
	}
	
	public List<String> getPlayers() {
		return i_players;
	}
	
	public void setPlayers(List<String> players) {
		i_players = players;
	}
	
	public State getGameState() {
		return i_gameState;
	}
	
	public void setGameState(State gameState) {
		i_gameState = gameState;
	}
	
	public List<Map<String, Hand>> getRounds() {
		return i_rounds;
	}
	
	protected void setRounds(List<Map<String, Hand>> rounds) {
		i_rounds = rounds;
	}

	public Date getDatePlayed() {
		return i_datePlayed;
	}

	public void setDatePlayed(Date datePlayed) {
		i_datePlayed = datePlayed;
	}

	public String getInstigator() {
		return i_instigator;
	}

	public void setInstigator(String instigator) {
		i_instigator = instigator;
	}

	public List<String> getCounters() {
		return i_counters;
	}

	protected void setCounters(List<String> counters) {
		i_counters = counters;
	}

	public List<String> getAllUsers() {
		return i_allUsers;
	}

	protected void setAllUsers(List<String> allUsers) {
		i_allUsers = allUsers;
	}
}
