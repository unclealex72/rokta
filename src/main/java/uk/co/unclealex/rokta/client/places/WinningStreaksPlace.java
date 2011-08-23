package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class WinningStreaksPlace extends GameFilterAwarePlace {

	public WinningStreaksPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof WinningStreaksPlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new WinningStreaksPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<WinningStreaksPlace> implements PlaceTokenizer<WinningStreaksPlace> {

		@Override
		protected WinningStreaksPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new WinningStreaksPlace(gameFilter);
		}
	}
}
