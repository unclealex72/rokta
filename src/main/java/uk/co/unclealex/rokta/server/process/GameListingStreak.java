package uk.co.unclealex.rokta.server.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Game;

/**
 * @author alex
 *
 */
public class GameListingStreak implements Comparable<GameListingStreak> {

	private String i_personName;
	private SortedSet<Game> i_games;
	private boolean i_current;
	
	/**
	 * @param person
	 * @param games
	 */
	public GameListingStreak(String personName, SortedSet<Game> games, boolean current) {
		super();
		i_personName = personName;
		i_games = games;
		i_current = current;
	}
	
	public int compareTo(GameListingStreak o) {
		int cmp = o.getLength() - getLength();
		if (cmp != 0) { return cmp; }
		cmp = o.getFirstGame().compareTo(getFirstGame());
		if (cmp != 0) { return cmp; }
		return getPersonName().compareTo(o.getPersonName());
	}
	
	public int getLength() {
		return getGames().size();
	}
	
	public Game getFirstGame() {
		return getGames().first();
	}

	public Game getLastGame() {
		return getGames().last();
	}

	/**
	 * @return the games
	 */
	public SortedSet<Game> getGames() {
		return i_games;
	}
	/**
	 * @param games the games to set
	 */
	public void setGames(SortedSet<Game> games) {
		i_games = games;
	}
	
	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return i_current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		i_current = current;
	}

	public String getPersonName() {
		return i_personName;
	}

	public void setPersonName(String personName) {
		i_personName = personName;
	}
	
	
}
