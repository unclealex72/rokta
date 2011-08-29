/**
 * 
 */
package uk.co.unclealex.rokta.server.process;


import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Person;

/**
 * @author alex
 *
 */
public interface PersonService {

	public Person getExemptPlayer(Date date);	
	public void changePassword(String name, String newPassword);
	public SortedSet<String> getAllUsernames();
	public SortedSet<String> getAllPlayerNames();
	void changeGraphingColour(String name, String colourName);
	public void resetAllPasswords();
}
