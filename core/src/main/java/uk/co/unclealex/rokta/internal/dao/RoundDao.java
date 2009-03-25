/**
 * 
 */
package uk.co.unclealex.rokta.internal.dao;

import uk.co.unclealex.rokta.internal.model.Hand;
import uk.co.unclealex.rokta.internal.model.Person;

/**
 * @author alex
 *
 */
public interface RoundDao {
	
	public int countOpeningGambitsByPersonAndHand(Person person, Hand hand);
	
}
