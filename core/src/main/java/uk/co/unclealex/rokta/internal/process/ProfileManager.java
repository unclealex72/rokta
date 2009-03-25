/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Hand;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;

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
