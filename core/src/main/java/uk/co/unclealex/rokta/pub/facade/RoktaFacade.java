package uk.co.unclealex.rokta.pub.facade;

import java.awt.Dimension;
import java.io.Writer;
import java.util.SortedSet;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.views.League;

public interface RoktaFacade {

	public SortedSet<Person> getAllUsers();
	public League getLeague(GameFilter gameFilter, DateTime now);
	public void drawLeagueGraph(GameFilter gameFilter, Dimension size, int maximumColumns, Writer writer);
	public void drawHandDistributionGraph(GameFilter gameFilter, Person person, Dimension size, Writer writer);
	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, Person person, Dimension size, Writer writer);

	public StreakLeague getWinningStreaks(GameFilter gameFilter, int targetSize);
	public StreakLeague getLosingStreaks(GameFilter gameFilter, int targetSize);
	public StreakLeague getCurrentWinningStreaks(GameFilter gameFilter, int targetSize);
	public StreakLeague getCurrentLosingStreaks(GameFilter gameFilter, int targetSize);
}
