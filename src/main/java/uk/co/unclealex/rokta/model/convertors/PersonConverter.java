package uk.co.unclealex.rokta.model.convertors;

import java.util.Map;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.PersonDAO;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

public class PersonConverter extends WebWorkTypeConverter {

	private PersonDAO i_personDAO;
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return getPersonDAO().getPersonByName(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return ((Person) o).getName();
	}

	public PersonDAO getPersonDAO() {
		return i_personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		i_personDAO = personDAO;
	}

}
