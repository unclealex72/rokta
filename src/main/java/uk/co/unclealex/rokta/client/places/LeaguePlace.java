package uk.co.unclealex.rokta.client.places;

import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class LeaguePlace extends RoktaPlace {

	public LeaguePlace(GameFilter gameFilter) {
		super(gameFilter);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof LeaguePlace) && isEqual((RoktaPlace) other);
	}
	
	@Override
	public RoktaPlace withGameFilter(GameFilter gameFilter) {
		return new LeaguePlace(gameFilter);
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends RoktaPlace.Tokenizer<LeaguePlace> implements PlaceTokenizer<LeaguePlace> {

		@Override
		protected LeaguePlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new LeaguePlace(gameFilter);
		}
	}
}
