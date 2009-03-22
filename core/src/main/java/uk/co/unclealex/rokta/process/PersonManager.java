/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Date;

import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public interface PersonManager {

	public Person getExemptPlayer(Date date);
	public boolean currentlyPlaying(Person player, Date date);
	
	public boolean changePassword(String name, String currentPassword, String newPassword);
}
