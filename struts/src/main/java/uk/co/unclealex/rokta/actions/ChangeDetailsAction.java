/**
 * 
 */
package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public class ChangeDetailsAction extends DetailsAction {

	private Colour i_colour;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.RoktaAction#executeInternal()
	 */
	@Override
	protected String executeInternal() {
		super.executeInternal();
		Person person = getPerson();
		person.setColour(getColour());
		getPersonDao().store(person);
		return SUCCESS;
	}

	/**
	 * @return the colour
	 */
	public Colour getColour() {
		return i_colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(Colour colour) {
		i_colour = colour;
	}
}
