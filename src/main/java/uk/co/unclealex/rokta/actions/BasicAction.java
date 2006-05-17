package uk.co.unclealex.rokta.actions;

import java.util.SortedSet;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.PersonDAO;

import com.opensymphony.xwork.ActionSupport;

public abstract class BasicAction extends ActionSupport {

	private SortedSet<Person> i_everybody;
	private PersonDAO i_personDAO;
	
	@Override
	public String execute() throws InvalidRoundException {
		setEverybody(getPersonDAO().getEverybody());
		return executeInternal();
	}

	protected abstract String executeInternal() throws InvalidRoundException;

	public SortedSet<Person> getEverybody() {
		return i_everybody;
	}

	public void setEverybody(SortedSet<Person> everybody) {
		i_everybody = everybody;
	}

	public PersonDAO getPersonDAO() {
		return i_personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		i_personDAO = personDAO;
	}
}
