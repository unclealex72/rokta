package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;

public class PlayerProfile implements Serializable {

	private Map<Hand, Long> i_handCounts;
	private Map<Hand, Long> i_openingHandCounts;
	private Colour i_colour;
	
	protected PlayerProfile() {
		// Default constructor for serialisation.
	}
	
	public PlayerProfile(Map<Hand, Long> handCounts, Map<Hand, Long> openingHandCounts, Colour colour) {
		super();
		i_handCounts = handCounts;
		i_openingHandCounts = openingHandCounts;
		i_colour = colour;
	}

	public Map<Hand, Long> getHandCounts() {
		return i_handCounts;
	}

	public Map<Hand, Long> getOpeningHandCounts() {
		return i_openingHandCounts;
	}

	public Colour getColour() {
		return i_colour;
	}
	
	
}
