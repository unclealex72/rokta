package uk.co.unclealex.rokta.server.dao;

import org.hibernate.Query;

import uk.co.unclealex.rokta.client.filter.GameFilter;

public interface GameFilterSupport {

	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters);

	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters, char gameAlias);

	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters,
			String postWhereClause);

	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters, char gameAlias,
			String postWhereClause);

}