package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;

public class LoggedInUser implements Serializable {

	private String i_username;
	private Colour i_colour;
	
	protected LoggedInUser() {
		// Constructor for serialisation.
	}
	
	public LoggedInUser(String username, Colour colour) {
		super();
		i_username = username;
		i_colour = colour;
	}

	public String getUsername() {
		return i_username;
	}

	public Colour getColour() {
		return i_colour;
	}
}
