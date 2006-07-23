package uk.co.unclealex.rokta.actions;

import java.util.SortedSet;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.PersonDao;

import com.opensymphony.xwork.ActionSupport;

public abstract class BasicAction extends ActionSupport {

	private SortedSet<Person> i_everybody;
	private PersonDao i_personDao;
	
	@Override
	public String execute() throws InvalidRoundException {
		setEverybody(getPersonDao().getEverybody());
		return executeInternal();
	}

	protected abstract String executeInternal() throws InvalidRoundException;

	public SortedSet<Person> getEverybody() {
		return i_everybody;
	}

	public void setEverybody(SortedSet<Person> everybody) {
		i_everybody = everybody;
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}
}
