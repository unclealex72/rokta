package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;


public enum Hand implements Comparable<Hand>, Serializable {
	ROCK(1, "Rock"), SCISSORS(2, "Scissors"), PAPER(3, "Paper");

	private int i_internalRepresentation;
	private String i_description;
	
	private Hand(int internalRepresentation, String description) {
		i_internalRepresentation = internalRepresentation;
		i_description = description;
	}

	public boolean beats(Hand other) {
		int otherInternalRepresentation = other.getInternalRepresentation();
		int internalRepresentation = getInternalRepresentation();
		return
			(internalRepresentation == 1 && otherInternalRepresentation == 2) ||
			(internalRepresentation == 2 && otherInternalRepresentation == 3) ||
			(internalRepresentation == 3 && otherInternalRepresentation == 1);
	}
	
	public String getDescription() {
		return i_description;
	}
	
	public int getInternalRepresentation() {
		return i_internalRepresentation;
	}
}
