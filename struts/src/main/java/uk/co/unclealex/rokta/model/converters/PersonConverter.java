package uk.co.unclealex.rokta.model.converters;

import java.util.Map;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.PersonDao;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

public class PersonConverter extends WebWorkTypeConverter {

	private PersonDao i_personDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return getPersonDao().getPersonByName(values[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		return ((Person) o).getName();
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

}
