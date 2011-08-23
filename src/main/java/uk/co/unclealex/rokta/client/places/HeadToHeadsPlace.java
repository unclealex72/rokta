package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class HeadToHeadsPlace extends GameFilterAwarePlace {

	public HeadToHeadsPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof HeadToHeadsPlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new HeadToHeadsPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<HeadToHeadsPlace> implements PlaceTokenizer<HeadToHeadsPlace> {

		@Override
		protected HeadToHeadsPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new HeadToHeadsPlace(gameFilter);
		}
	}
}
