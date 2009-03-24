/**
 * 
 */
package uk.co.unclealex.rokta.process;


import org.joda.time.DateTime;

import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public interface PersonManager {

	public Person getExemptPlayer(DateTime date);
	public boolean currentlyPlaying(Person player, DateTime date);
	
	public boolean changePassword(String name, String currentPassword, String newPassword);
}
