package uk.co.unclealex.rokta.client.places;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class LeaguePlace extends GameFilterAwarePlace {

	public LeaguePlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof LeaguePlace) && isEqual((GameFilterAwarePlace) other);
	}
	
	@Override
	public GameFilterAwarePlace withGameFilter(GameFilter gameFilter) {
		return new LeaguePlace(gameFilter);
	}
	
	@Override
	public void accept(RoktaPlaceVisitor visitor) {
		visitor.visit(this);
	}
	
	public static class Tokenizer extends GameFilterAwarePlace.Tokenizer<LeaguePlace> implements PlaceTokenizer<LeaguePlace> {

		@Override
		protected LeaguePlace getPlace(GameFilter gameFilter) {
			return new LeaguePlace(gameFilter);
		}
	}
}
