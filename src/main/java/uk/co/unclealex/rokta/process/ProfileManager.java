/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedMap;

import org.apache.commons.lang.math.Fraction;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public interface ProfileManager {
	
	public SortedMap<Hand, Integer> countHands();

	public SortedMap<Hand, Integer> countOpeningGambits();

	public SortedMap<Person, Fraction> getHeadToHeadRoundWinRate();
	
	public Person getPerson();
	
	public void setPerson(Person person);
}
