package uk.co.unclealex.rokta.client.places;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class AdminPlace extends GameFilterAwarePlace {


	public AdminPlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof AdminPlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new AdminPlace(gameFilter);
	}
	
	@Override
	public void accept(RoktaPlaceVisitor visitor) {
		visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<AdminPlace> implements PlaceTokenizer<AdminPlace> {

		@Override
		protected AdminPlace getPlace(GameFilter gameFilter) {
			return new AdminPlace(gameFilter);
		}
	}
}
