package uk.co.unclealex.rokta.server.service;

/**
 * An interface for classes to make sure that all client requests are cached for
 * performance. The main use case for reading data from Rokta is generating
 * leagues and graphs at a given point in time. Using caching, there is no need
 * to recalculate a league or graph unless a new game is played or the day
 * changes.
 * 
 * @author alex
 * 
 */
public interface CacheService {

	public String CACHE_NAME = "roktaCache";

	/**
	 * A method needs to be called if cache information needs to be invalidated.
	 * That is, if a new game is entered or the day changes.
	 */
	public void clearCache();
}
