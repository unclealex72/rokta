package uk.co.unclealex.rokta.client.places;

import com.google.gwt.place.shared.Place;

public abstract class RoktaPlace extends Place {

	public abstract <T> T accept(RoktaPlaceVisitor<T> visitor);
}
