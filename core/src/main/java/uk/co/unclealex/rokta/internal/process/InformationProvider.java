/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;

/**
 * @author alex
 *
 */
public interface InformationProvider {

	public SortedSet<Game> getGames();
	public SortedSet<Person> getPeople();
	
}
