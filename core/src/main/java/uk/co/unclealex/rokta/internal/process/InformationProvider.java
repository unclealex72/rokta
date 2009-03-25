/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.pub.model.Game;
import uk.co.unclealex.rokta.pub.model.Person;

/**
 * @author alex
 *
 */
public interface InformationProvider {

	public SortedSet<Game> getGames();
	public SortedSet<Person> getPeople();
	
}
