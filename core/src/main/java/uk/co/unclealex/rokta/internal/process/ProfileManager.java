/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedMap;

import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;

/**
 * @author alex
 *
 */
public interface ProfileManager {
	
	public SortedMap<Hand, Integer> countHands(GameFilter gameFilter, Person person);

	public SortedMap<Hand, Integer> countOpeningGambits(GameFilter gameFilter, Person person);

	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate(GameFilter gameFilter, Person person);	
}
