package uk.co.unclealex.rokta.server.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Person;

public class RoktaUserDetailsService implements UserDetailsService {

	private PersonDao i_personDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		final Person person = getPersonDao().getPersonByName(username);
		if (person == null) {
			throw new UsernameNotFoundException("Cannot find user " + username);
		}
		return new RoktaUserDetails(person.getName(), person.getPassword());
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

}
