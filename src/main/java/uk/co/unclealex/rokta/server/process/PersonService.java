/**
 * 
 */
package uk.co.unclealex.rokta.server.process;


import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.shared.model.Colour;

/**
 * @author alex
 *
 */
public interface PersonService {

	public Person getExemptPlayer(Date date);	
	public SortedSet<String> getAllPlayerNames();
	public void changeGraphingColour(String name, Colour colour);
  public SortedSet<String> getAllUsernames();
}
