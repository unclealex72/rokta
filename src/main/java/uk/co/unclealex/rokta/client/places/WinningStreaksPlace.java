package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class WinningStreaksPlace extends RoktaPlace {

	public WinningStreaksPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof WinningStreaksPlace) && isEqual((RoktaPlace) other);
	}
	
	@Override
	public RoktaPlace withGameFilter(GameFilter gameFilter) {
		return new WinningStreaksPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends RoktaPlace.Tokenizer<WinningStreaksPlace> implements PlaceTokenizer<WinningStreaksPlace> {

		@Override
		protected WinningStreaksPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new WinningStreaksPlace(gameFilter);
		}
	}
}
