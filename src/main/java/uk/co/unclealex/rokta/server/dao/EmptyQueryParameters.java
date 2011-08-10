package uk.co.unclealex.rokta.server.dao;

import java.util.Collections;

import org.hibernate.Query;

class EmptyQueryParameters implements QueryParameters {
	@Override
	public void addRestrictions(Query query) {
		// Do nothing
	}
	@Override
	public Iterable<String> getParameters() {
		return Collections.emptySet();
	}
}