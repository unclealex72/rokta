/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.server.model.Person;

/**
 * @author alex
 *
 */
public interface RoundDao {
	
	public int countOpeningGambitsByPersonAndHand(Person person, Hand hand);
	
}
