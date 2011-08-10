package uk.co.unclealex.rokta.server.dao;

import org.hibernate.Query;

interface QueryParameters {
	void addRestrictions(Query query);
	Iterable<String> getParameters();
}