/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public interface InformationProvider {

	public SortedSet<Game> getGames();
	public SortedSet<Person> getPeople();
	
}
