/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import java.util.List;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

/**
 * @author alex
 *
 */
public interface RoundDao {
	
	public int countOpeningGambitsByPersonAndHand(Person person, Hand hand);
	
	public List<Round> getFinalRounds();
}
