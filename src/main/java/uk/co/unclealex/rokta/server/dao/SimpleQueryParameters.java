package uk.co.unclealex.rokta.server.dao;

import java.util.Arrays;
import java.util.List;

abstract class SimpleQueryParameters implements QueryParameters {
	private List<String> i_parameters;

	public SimpleQueryParameters(String... parameters) {
		super();
		i_parameters = Arrays.asList(parameters);
	}
	
	public Iterable<String> getParameters() {
		return i_parameters;
	}
}