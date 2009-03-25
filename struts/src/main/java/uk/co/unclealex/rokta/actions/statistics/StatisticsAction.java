/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import uk.co.unclealex.rokta.actions.RoktaAction;
import uk.co.unclealex.rokta.process.StatisticsManager;

/**
 * @author alex
 *
 */
public abstract class StatisticsAction extends RoktaAction {

	private StatisticsService i_statisticsManager;

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.RoktaAction#executeInternal()
	 */
	@Override
	protected abstract String executeInternal() throws Exception;
	/**
	 * @return the statisticsManager
	 */
	public StatisticsService getStatisticsManager() {
		return i_statisticsManager;
	}

	/**
	 * @param statisticsManager the statisticsManager to set
	 */
	public void setStatisticsManager(StatisticsService statisticsManager) {
		i_statisticsManager = statisticsManager;
	}
	
	public boolean isShowStatistics() {
		return true;
	}
}
