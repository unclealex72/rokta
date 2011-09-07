package uk.co.unclealex.rokta.client.places;

import java.util.Collections;
import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePlace extends RoktaPlace {

	private String i_username;
	
	public ProfilePlace(GameFilter gameFilter, String username) {
		super(gameFilter);
		i_username = username;
	}

	@Override
	public boolean equals(Object other) {
		return 
				(other instanceof ProfilePlace) && 
				isEqual((RoktaPlace) other) && 
				getUsername().equals(((ProfilePlace) other).getUsername());
	}
	
	@Override
	public RoktaPlace withGameFilter(GameFilter gameFilter) {
		return new ProfilePlace(gameFilter, getUsername());
	}
	
	@Override
	public <T> T accept(RoktaPlaceVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public static class Tokenizer extends RoktaPlace.Tokenizer<ProfilePlace> implements PlaceTokenizer<ProfilePlace> {

		@Override
		protected ProfilePlace getPlace(List<String> tokens, GameFilter gameFilter) {
			return new ProfilePlace(gameFilter, tokens.get(0));
		}

		@Override
		protected Iterable<String> formatTokens(ProfilePlace place) {
			return Collections.singleton(place.getUsername());
		}
	}

	public String getUsername() {
		return i_username;
	}
}
