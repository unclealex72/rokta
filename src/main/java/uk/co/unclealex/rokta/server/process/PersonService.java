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
	public SortedSet<String> getCurrentPlayerNames(Date date);
	
	public boolean changePassword(String name, String currentPassword, String newPassword);
}
