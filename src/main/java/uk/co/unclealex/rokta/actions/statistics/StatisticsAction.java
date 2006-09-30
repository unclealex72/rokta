/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import uk.co.unclealex.rokta.actions.BasicAction;
import uk.co.unclealex.rokta.process.StatisticsManager;

/**
 * @author alex
 *
 */
public abstract class StatisticsAction extends BasicAction {

	private StatisticsManager i_statisticsManager;

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.BasicAction#executeInternal()
	 */
	@Override
	protected abstract String executeInternal() throws Exception;
	/**
	 * @return the statisticsManager
	 */
	public StatisticsManager getStatisticsManager() {
		return i_statisticsManager;
	}

	/**
	 * @param statisticsManager the statisticsManager to set
	 */
	public void setStatisticsManager(StatisticsManager statisticsManager) {
		i_statisticsManager = statisticsManager;
	}
	
	public boolean isShowStatistics() {
		return true;
	}
}
