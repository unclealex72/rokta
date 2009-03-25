package uk.co.unclealex.rokta.pub.facade;

import java.awt.Dimension;
import java.io.Writer;
import java.util.SortedSet;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.Streak;

public interface RoktaFacade {

	public SortedSet<String> getAllUsersNames();
	public boolean logIn(String username, String password);
	public String getCurrentlyLoggedInUser();
	public void logOut();
	
	public League getLeague(GameFilter gameFilter, int maximumLeagues, DateTime now);
	public void drawLeagueGraph(GameFilter gameFilter, Dimension size, int maximumColumns, Writer writer);
	public void drawHandDistributionGraph(GameFilter gameFilter, String personName, Dimension size, Writer writer);
	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, String personName, Dimension size, Writer writer);

	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, int targetSize);
	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, int targetSize);
	public SortedSet<Streak> getCurrentWinningStreaks(GameFilter gameFilter);
	public SortedSet<Streak> getCurrentLosingStreaks(GameFilter gameFilter);
	public SortedSet<Streak> getLosingStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);
	public SortedSet<Streak> getWinningStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);
}
