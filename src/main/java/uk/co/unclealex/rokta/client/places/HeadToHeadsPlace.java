package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class HeadToHeadsPlace extends RoktaPlace {

	public HeadToHeadsPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof HeadToHeadsPlace) && isEqual((RoktaPlace) other);
	}
	
	@Override
	public RoktaPlace withGameFilter(GameFilter gameFilter) {
		return new HeadToHeadsPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends RoktaPlace.Tokenizer<HeadToHeadsPlace> implements PlaceTokenizer<HeadToHeadsPlace> {

		@Override
		protected HeadToHeadsPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new HeadToHeadsPlace(gameFilter);
		}
	}
}
