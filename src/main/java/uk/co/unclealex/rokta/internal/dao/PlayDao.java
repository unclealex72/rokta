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
public interface PlayDao {

	public int countByPersonAndHand(Person person, Hand hand);
}
