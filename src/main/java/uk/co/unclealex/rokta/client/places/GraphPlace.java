package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class GraphPlace extends RoktaPlace {

	public GraphPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof GraphPlace) && isEqual((RoktaPlace) other);
	}
	
	@Override
	public RoktaPlace withGameFilter(GameFilter gameFilter) {
		return new GraphPlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends RoktaPlace.Tokenizer<GraphPlace> implements PlaceTokenizer<GraphPlace> {

		@Override
		protected GraphPlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new GraphPlace(gameFilter);
		}
	}
}
