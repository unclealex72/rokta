package uk.co.unclealex.rokta.actions;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Person;

public class DetailsAction extends RoktaAction {

	private SortedSet<Colour> i_allColours;
	private Person i_person;

	@Override
	protected String executeInternal() {
		setAllColours(getColourDao().getColours());
		setPerson(getPersonDao().getPersonByName(getPrincipalProxy().getUserPrincipal().getName()));
		return SUCCESS;
	}
	
	public SortedSet<Colour> getAllColours() {
		return i_allColours;
	}

	public void setAllColours(SortedSet<Colour> allColours) {
		i_allColours = allColours;
	}

	public Person getPerson() {
		return i_person;
	}

	public void setPerson(Person person) {
		i_person = person;
	}

}
