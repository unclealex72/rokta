/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import java.util.SortedMap;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.Hand;

/**
 * @author alex
 *
 */
public interface PlayDao {
	public SortedMap<String, SortedMap<Hand, Long>> countPlaysByPersonAndHand(GameFilter gameFilter);
	public SortedMap<String, SortedMap<Hand, Long>> countOpeningPlaysByPersonAndHand(GameFilter gameFilter);
}
