/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public interface PlayDao {

	public int countByPersonAndHand(Person person, Hand hand);
}
