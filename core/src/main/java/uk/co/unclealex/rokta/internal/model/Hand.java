package uk.co.unclealex.rokta.internal.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public enum Hand implements Comparable<Hand>, Serializable {
	ROCK(1, "Rock"), SCISSORS(2, "Scissors"), PAPER(3, "Paper");

	private static Hand[] s_allHands = { ROCK, SCISSORS, PAPER };
	private static List<Hand> s_allHandsList = Collections.unmodifiableList(Arrays.asList(s_allHands));
	
	public static List<Hand> getAllHands() {
		return s_allHandsList;
	}
	
	private int i_internalRepresentation;
	private String i_description;
	
	private Hand(int internalRepresentation, String description) {
		i_internalRepresentation = internalRepresentation;
		i_description = description;
	}

	public boolean beats(Hand other) {
		return
			(i_internalRepresentation == 1 && other.i_internalRepresentation == 2) ||
			(i_internalRepresentation == 2 && other.i_internalRepresentation == 3) ||
			(i_internalRepresentation == 3 && other.i_internalRepresentation == 1);
	}
	
	public String getDescription() {
		return i_description;
	}
}
