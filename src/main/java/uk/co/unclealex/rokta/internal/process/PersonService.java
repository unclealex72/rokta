/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;


import java.util.Date;

import uk.co.unclealex.rokta.internal.model.Person;

/**
 * @author alex
 *
 */
public interface PersonService {

	public Person getExemptPlayer(Date date);
	public boolean currentlyPlaying(String playerName, Date date);
	
	public boolean changePassword(String name, String currentPassword, String newPassword);
}