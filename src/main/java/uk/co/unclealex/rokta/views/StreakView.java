/**
 * 
 */
package uk.co.unclealex.rokta.views;

import java.util.SortedSet;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;

/**
 * @author alex
 *
 */
public class StreakView implements Comparable<StreakView> {

	private Person i_person;
	private Game i_firstGame;
	private Game i_lastGame;
	private int i_length;
	private boolean i_current;
	
	public int compareTo(StreakView o) {
		int cmp = -(new Integer(getLength()).compareTo(o.getLength()));
		if (cmp != 0) { return cmp; }
		cmp = getFirstGame().compareTo(o.getFirstGame());
		if (cmp != 0) { return cmp; }
		return getPerson().compareTo(o.getPerson());
	}
	
	private static Transformer<Streak, StreakView> s_streakTransformer =
		new Transformer<Streak, StreakView>() {
			public StreakView transform(Streak streak) {
				StreakView result = new StreakView();
				result.setPerson(streak.getPerson());
				SortedSet<Game> games = streak.getGames();
				result.setFirstGame(games.first());
				result.setLastGame(games.last());
				result.setLength(games.size());
				result.setCurrent(streak.isCurrent());
				return result;				
			}
		};
		
	public static Transformer<Streak, StreakView> getStreakViewTransformer() {
		return s_streakTransformer;
	}
	/**
	 * @return the firstGame
	 */
	public Game getFirstGame() {
		return i_firstGame;
	}
	/**
	 * @param firstGame the firstGame to set
	 */
	public void setFirstGame(Game firstGame) {
		i_firstGame = firstGame;
	}
	/**
	 * @return the lastGame
	 */
	public Game getLastGame() {
		return i_lastGame;
	}
	/**
	 * @param lastGame the lastGame to set
	 */
	public void setLastGame(Game lastGame) {
		i_lastGame = lastGame;
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return i_length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		i_length = length;
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
