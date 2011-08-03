/**
 * 
 */
package uk.co.unclealex.rokta.internal.dao;

import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.views.Hand;

/**
 * @author alex
 *
 */
public interface RoundDao {
	
	public int countOpeningGambitsByPersonAndHand(Person person, Hand hand);
	
}
