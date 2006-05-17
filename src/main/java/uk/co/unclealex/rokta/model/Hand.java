package uk.co.unclealex.rokta.model;


public  enum Hand {
	ROCK(1, "Rock"), SCISSORS(2, "Scissors"), PAPER(3, "Paper");

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
