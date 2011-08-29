package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;

public class LoggedInUser implements Serializable {

	private String i_username;
	private ColourView i_colourView;
	
	protected LoggedInUser() {
		// Constructor for serialisation.
	}
	
	public LoggedInUser(String username, ColourView colourView) {
		super();
		i_username = username;
		i_colourView = colourView;
	}

	public String getUsername() {
		return i_username;
	}

	public ColourView getColourView() {
		return i_colourView;
	}
}
