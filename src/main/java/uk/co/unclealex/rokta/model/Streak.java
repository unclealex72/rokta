/**
 * 
 */
package uk.co.unclealex.rokta.model;

import java.io.Serializable;
import java.util.SortedSet;

/**
 * @author alex
 *
 */
public class Streak implements Serializable {

	private Person i_person;
	private SortedSet<Game> i_games;
	
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
	public Streak(Person person, SortedSet<Game> games) {
		super();
		i_person = person;
		i_games = games;
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
	
	
}
