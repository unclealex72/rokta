package uk.co.unclealex.rokta.pub.filter.factory;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface GameFilterFactory {

	public GameFilter createSinceGameFilter(DateTime since);
	public GameFilter createBeforeGameFilter(DateTime before);
	public GameFilter createBetweenGameFilter(DateTime from, DateTime to);
	public GameFilter createAllGameFilter();
	
	public GameFilter createYearGameFilter(int year);
	public GameFilter createMonthGameFilter(int month, int year);
	public GameFilter createWeekGameFilter(int week, int year);
}
