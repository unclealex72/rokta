/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.SortedMap;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.client.views.WinLoseCounter;
import uk.co.unclealex.rokta.server.model.Person;

/**
 * @author alex
 *
 */
public interface ProfileManager {
	
	public SortedMap<Hand, Integer> countHands(GameFilter gameFilter, Person person);

	public SortedMap<Hand, Integer> countOpeningGambits(GameFilter gameFilter, Person person);

	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate(GameFilter gameFilter, Person person);	
}
