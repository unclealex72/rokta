package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;

public class HandDistribution implements Serializable {

	private Map<Hand, Integer> i_gamesByHand;

	public HandDistribution(Map<Hand, Integer> gamesByHand) {
		super();
		i_gamesByHand = gamesByHand;
	}

	protected HandDistribution() {
		super();
		// Default constructor for serialisation
	}

	public Map<Hand, Integer> getGamesByHand() {
		return i_gamesByHand;
	}
	
	
}
