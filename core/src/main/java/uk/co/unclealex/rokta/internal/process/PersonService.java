/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;


import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.model.Person;

/**
 * @author alex
 *
 */
public interface PersonService {

	public Person getExemptPlayer(DateTime date);
	public boolean currentlyPlaying(Person player, DateTime date);
	
	public boolean changePassword(String name, String currentPassword, String newPassword);
}
