package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;

public class PlayerProfile implements Serializable {

	private Map<Hand, Long> i_handCounts;
	private Map<Hand, Long> i_openingHandCounts;
	private String i_colourName;
	
	protected PlayerProfile() {
		// Default constructor for serialisation.
	}
	
	public PlayerProfile(Map<Hand, Long> handCounts, Map<Hand, Long> openingHandCounts, String colourName) {
		super();
		i_handCounts = handCounts;
		i_openingHandCounts = openingHandCounts;
		i_colourName = colourName;
	}

	public Map<Hand, Long> getHandCounts() {
		return i_handCounts;
	}

	public Map<Hand, Long> getOpeningHandCounts() {
		return i_openingHandCounts;
	}

	public String getColourName() {
		return i_colourName;
	}
	
	
}
