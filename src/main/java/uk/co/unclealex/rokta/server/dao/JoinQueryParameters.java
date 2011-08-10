package uk.co.unclealex.rokta.server.dao;

import java.util.Arrays;
import java.util.Collections;

import org.hibernate.Query;

import com.google.common.collect.Iterables;

class JoinQueryParameters implements QueryParameters {
	private Iterable<QueryParameters> i_queryParameters;

	public JoinQueryParameters(Iterable<QueryParameters> queryParameters) {
		super();
		i_queryParameters = queryParameters;
	}
	
	public JoinQueryParameters(QueryParameters... queryParameters) {
		super();
		i_queryParameters = Arrays.asList(queryParameters);
	}

	@Override
	public void addRestrictions(Query query) {
		for (QueryParameters queryParameters : getQueryParameters()) {
			queryParameters.addRestrictions(query);
		}
	}
	
	@Override
	public Iterable<String> getParameters() {
		Iterable<String> result = Collections.emptySet();
		for (QueryParameters queryParameters : getQueryParameters()) {
			result = Iterables.concat(result, queryParameters.getParameters());
		}
		return result;
	}
	
	public Iterable<QueryParameters> getQueryParameters() {
		return i_queryParameters;
	}
}