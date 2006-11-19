/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.actions.RoktaAction;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.process.CalendarFieldComparator;
import uk.co.unclealex.rokta.process.LeagueManager;
import uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer;
import uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate;
import uk.co.unclealex.rokta.process.league.granularity.InfiniteGranularityPredicate;
import uk.co.unclealex.rokta.process.league.granularity.MonthGranularityPredicate;
import uk.co.unclealex.rokta.process.league.granularity.TwoMonthGranularityPredicate;
import uk.co.unclealex.rokta.process.league.granularity.WeekGranularityPredicate;
import uk.co.unclealex.rokta.process.league.granularity.YearGranularityPredicate;
import uk.co.unclealex.rokta.process.league.milestone.GamesLeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.league.milestone.LeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.league.milestone.QuotientLeagueMilestonePredicate;

/**
 * @author alex
 *
 */
public abstract class LeagueAction extends RoktaAction {

	private static SortedSet<GranularityPredicate> s_granularityPredicates = new TreeSet<GranularityPredicate>();
	static {
		s_granularityPredicates.add(new WeekGranularityPredicate());
		s_granularityPredicates.add(new MonthGranularityPredicate());
		s_granularityPredicates.add(new TwoMonthGranularityPredicate());
		s_granularityPredicates.add(new YearGranularityPredicate());
		s_granularityPredicates.add(new InfiniteGranularityPredicate());
	}
	
	private LeagueGraphDatasetProducer i_leagueGraphDatasetProducer;
	private LeagueManager i_leagueManager;
	private League i_league;
	private String i_leagueTitle;
	private String i_graphTitle;
	
	private GranularityPredicate i_granularityPredicate;
	@Override
	protected String executeInternal() throws Exception {
		SortedSet<Game> games = getGames();
		if (games.isEmpty()) {
			setLeague(new League());
			getLeagueGraphDatasetProducer().setLeaguesByGame(new TreeMap<Game, League>());
		}
		else {
			setGranularityPredicate(produceGranularityPredicate(games));
			List<LeagueMilestonePredicate> predicates = new ArrayList<LeagueMilestonePredicate>(2);
			predicates.add(produceTableLeagueMilestonePredicate(getGames()));
			predicates.add(produceGraphLeagueMilestonePredicate(getGames()));
			
			LeagueManager manager = getLeagueManager();
			manager.setCurrentDate(new Date());
			manager.setGames(getGames());
			manager.setLeagueMilestonePredicates(predicates);
			
			List<SortedMap<Game, League>> leaguesByGameForPredicates = manager.generateLeagues();
			SortedMap<Game, League> leaguesByGameForTable = leaguesByGameForPredicates.get(0);
			SortedMap<Game, League> leaguesByGameForGraph = leaguesByGameForPredicates.get(1);
			setLeague(leaguesByGameForTable.get(leaguesByGameForTable.lastKey()));
			getLeagueGraphDatasetProducer().setLeaguesByGame(leaguesByGameForGraph);
			getLeagueGraphDatasetProducer().setGameDescriptor(getGranularityPredicate().getGameDescriptor());
			setLeagueTitle(getLeagueTitleInternal());
			setGraphTitle(getGraphTitleInternal());
		}
		return SUCCESS;
	}

	public abstract SortedSet<Game> getGames() throws Exception;

	public abstract String getLeagueTitleInternal();
	public abstract String getGraphTitleInternal();
	
	public GranularityPredicate produceGranularityPredicate(SortedSet<Game> games) {
		final long millisBetweenGames =
			games.last().getDatePlayed().getTime() - games.first().getDatePlayed().getTime();
		GranularityPredicate granularityPredicate =
			CollectionUtils.find(
					s_granularityPredicates,
					new Predicate<GranularityPredicate>() {
						public boolean evaluate(GranularityPredicate predicate) {
							return predicate.evaluate(millisBetweenGames);
						}
					});
		return granularityPredicate;
	}
	/**
	 * The the last game is infact the last game actually played then we want both
	 * the penultimate and last games. Otherwise, we just want the last game.
	 *
	 * @param games
	 * @return
	 */
	public final LeagueMilestonePredicate produceTableLeagueMilestonePredicate(SortedSet<Game> games) {
		List<Game> milestoneGames = new LinkedList<Game>();
		
		Game lastGame = games.last();
		milestoneGames.add(lastGame);

		if (lastGame.equals(getGameDao().getLastGame())) {
			SortedSet<Game> previousGames = new TreeSet<Game>();
			previousGames.addAll(games);
			previousGames.remove(lastGame);
			
			
			if (!previousGames.isEmpty()) {
				milestoneGames.add(previousGames.last()); 
			}
		}
		return new GamesLeagueMilestonePredicate(milestoneGames);

	}

	/**
	 * The default predicate is to show games per week.
	 * @param games
	 * @return
	 */
	public LeagueMilestonePredicate produceGraphLeagueMilestonePredicate(SortedSet<Game> games) {
		return
			new QuotientLeagueMilestonePredicate(
					new CalendarFieldComparator(getGranularityPredicate().getGranularity().getCalendarField()));
	}

	public boolean isShowLeague() {
		return true;
	}

	public Transformer<Game, String> getGameDescriptor() {
		return new Transformer<Game, String>() {
			public String transform(Game game) {
				return getGranularityPredicate().formatDate(game.getDatePlayed());
			}
		};
	}
	
	/**
	 * @return the leagueGraphDatasetProducer
	 */
	public LeagueGraphDatasetProducer getLeagueGraphDatasetProducer() {
		return i_leagueGraphDatasetProducer;
	}

	/**
	 * @param leagueGraphDatasetProducer the leagueGraphDatasetProducer to set
	 */
	public void setLeagueGraphDatasetProducer(LeagueGraphDatasetProducer leagueGraphDatasetProducer) {
		i_leagueGraphDatasetProducer = leagueGraphDatasetProducer;
	}

	public League getLeague() {
		return i_league;
	}

	public void setLeague(League league) {
		i_league = league;
	}

	/**
	 * @return the leagueManager
	 */
	public LeagueManager getLeagueManager() {
		return i_leagueManager;
	}

	/**
	 * @param leagueManager the leagueManager to set
	 */
	public void setLeagueManager(LeagueManager leagueManager) {
		i_leagueManager = leagueManager;
	}

	/**
	 * @return the granularityPredicate
	 */
	public GranularityPredicate getGranularityPredicate() {
		return i_granularityPredicate;
	}

	/**
	 * @param granularityPredicate the granularityPredicate to set
	 */
	public void setGranularityPredicate(GranularityPredicate granularityPredicate) {
		i_granularityPredicate = granularityPredicate;
	}

	/**
	 * @return the leagueTitle
	 */
	public final String getLeagueTitle() {
		return i_leagueTitle;
	}

	/**
	 * @param leagueTitle the leagueTitle to set
	 */
	public void setLeagueTitle(String leagueTitle) {
		i_leagueTitle = leagueTitle;
	}

	/**
	 * @return the graphTitle
	 */
	public final String getGraphTitle() {
		return i_graphTitle;
	}

	/**
	 * @param graphTitle the graphTitle to set
	 */
	public void setGraphTitle(String graphTitle) {
		i_graphTitle = graphTitle;
	}

}
