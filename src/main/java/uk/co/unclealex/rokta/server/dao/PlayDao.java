/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import java.util.SortedMap;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.shared.model.Hand;

/**
 * @author alex
 *
 */
public interface PlayDao {
	public SortedMap<Hand, Integer> countPlaysByPersonAndHand(GameFilter gameFilter, Person person);
	public SortedMap<Hand, Integer> countOpeningPlaysByPersonAndHand(GameFilter gameFilter, Person person);
}
