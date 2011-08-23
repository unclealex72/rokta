package uk.co.unclealex.rokta.client.places;

import uk.co.unclealex.rokta.shared.model.Game;

import com.google.gwt.place.shared.PlaceTokenizer;

public class GamePlace extends RoktaPlace {

	private final Game i_game;

	public GamePlace() {
		this(new Game());
	}
	
	public GamePlace(Game game) {
		super();
		i_game = game;
	}

	@Override
	public boolean equals(Object other) {
		return 
				(other instanceof GamePlace) && getGame().equals(((GamePlace) other).getGame());
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer implements PlaceTokenizer<GamePlace> {

		@Override
		public GamePlace getPlace(String token) {
			return new GamePlace(new Game(token));
		}

		@Override
		public String getToken(GamePlace place) {
			return place.getGame().asToken();
		}

	}

	public Game getGame() {
		return i_game;
	}
}
