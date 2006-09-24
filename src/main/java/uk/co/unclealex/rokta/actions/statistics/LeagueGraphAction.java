/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.Calendar;
import java.util.SortedSet;

import uk.co.unclealex.rokta.actions.BasicAction;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.CalendarFieldTransformer;
import uk.co.unclealex.rokta.process.InformationProvider;
import uk.co.unclealex.rokta.process.LeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.QuotientLeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer;

/**
 * @author alex
 *
 */
public class LeagueGraphAction extends BasicAction {

	private LeagueGraphDatasetProducer i_leagueGraphDatasetProducer;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork.ActionSupport#execute()
	 */
	@Override
	public String executeInternal() {
		LeagueMilestonePredicate milestonePredicate =
			new QuotientLeagueMilestonePredicate<Integer>(new CalendarFieldTransformer(Calendar.WEEK_OF_YEAR));
		LeagueGraphDatasetProducer producer = getLeagueGraphDatasetProducer();
		producer.setInformationProvider(
				new InformationProvider() {
					public SortedSet<Game> getGames() {
						return getGameDao().getAllGames();
					}
					public SortedSet<Person> getPeople() {
						return null;
					}
				});
		producer.setLeagueMilestonePredicate(milestonePredicate);
		return SUCCESS;
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
	public void setLeagueGraphDatasetProducer(
			LeagueGraphDatasetProducer leagueGraphDatasetProducer) {
		i_leagueGraphDatasetProducer = leagueGraphDatasetProducer;
	}
}
