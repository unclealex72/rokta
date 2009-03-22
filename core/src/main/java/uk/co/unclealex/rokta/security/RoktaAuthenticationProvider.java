/**
 * 
 */
package uk.co.unclealex.rokta.security;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.PersonDao;

/*
 * Created by: aj016368
 * Date: 4 Dec 2006
 * Time: 14:19:35
 */
public class RoktaAuthenticationProvider extends
    AbstractUserDetailsAuthenticationProvider {

	private PersonDao i_personDao;
	private PasswordEncoder i_passwordEncoder;
  /**
   * As we have no concept of enabled or locked accounts, we can do all our authentication
   * in the retrieveUser method.
   */
  @Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
  }

  /**
   * Authenticate a user using a metasolv authenticator.
   */
  @Override
	protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
		String password = authentication.getCredentials().toString();
		Person person = getPersonDao().findPersonByNameAndPassword(username, getPasswordEncoder().encode(password));
		if (person == null) {
	    throw new BadCredentialsException(messages.getMessage(
	        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), null);
		}
		return new User(
				person.getName(), password, true, true, true, true,
				new GrantedAuthorityImpl[] {
					new GrantedAuthorityImpl("ROLE_USER")});
  }

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	public PasswordEncoder getPasswordEncoder() {
		return i_passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		i_passwordEncoder = passwordEncoder;
	}

}
