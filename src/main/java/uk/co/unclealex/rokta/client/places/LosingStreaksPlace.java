package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class LosingStreaksPlace extends GameFilterAwarePlace {

	public LosingStreaksPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof LosingStreaksPlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new LosingStreaksPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<LosingStreaksPlace> implements PlaceTokenizer<LosingStreaksPlace> {

		@Override
		protected LosingStreaksPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new LosingStreaksPlace(gameFilter);
		}
	}
}
