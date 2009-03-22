/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.WinLoseCounter;

/**
 * @author alex
 *
 */
public interface ProfileManager {
	
	public SortedMap<Hand, Integer> countHands();

	public SortedMap<Hand, Integer> countOpeningGambits();

	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate();
	
	public Person getPerson();
	
	public void setPerson(Person person);
  
  public SortedSet<Game> getGames();

  public void setGames(SortedSet<Game> games);

}
