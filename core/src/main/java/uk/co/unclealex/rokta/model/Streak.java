/**
 * 
 */
package uk.co.unclealex.rokta.model;

import java.util.SortedSet;

/**
 * @author alex
 *
 */
public class Streak implements Comparable<Streak> {

	private Person i_person;
	private SortedSet<Game> i_games;
	private boolean i_current;
	
	/**
	 * 
	 */
	public Streak() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param person
	 * @param games
	 */
	public Streak(Person person, SortedSet<Game> games, boolean current) {
		super();
		i_person = person;
		i_games = games;
		i_current = current;
	}
	
	public int compareTo(Streak o) {
		int cmp = -(new Integer(getLength()).compareTo(o.getLength()));
		if (cmp != 0) { return cmp; }
		cmp = -getFirstGame().compareTo(o.getFirstGame());
		if (cmp != 0) { return cmp; }
		return getPerson().compareTo(o.getPerson());
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
	 * @return the person
	 */
	public Person getPerson() {
		return i_person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		i_person = person;
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
	
	
}
