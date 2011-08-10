package uk.co.unclealex.rokta.client.places;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePlace extends GameFilterAwarePlace {


	public ProfilePlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof ProfilePlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new ProfilePlace(gameFilter);
	}
	
	@Override
	public void accept(RoktaPlaceVisitor visitor) {
		visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<ProfilePlace> implements PlaceTokenizer<ProfilePlace> {

		@Override
		protected ProfilePlace getPlace(GameFilter gameFilter) {
			return new ProfilePlace(gameFilter);
		}

	}
}
