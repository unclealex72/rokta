/**
 * 
 */
package uk.co.unclealex.rokta.internal.process.dataset;

import org.jfree.data.general.Dataset;

import uk.co.unclealex.rokta.internal.process.InformationProvider;
import uk.co.unclealex.rokta.internal.process.StatisticsService;

/**
 * @author alex
 *
 */
public abstract class StatisticsAwareDatasetProducer<D extends Dataset> implements DatasetProducer<D> {

	private StatisticsService i_statisticsManager;
	private InformationProvider i_informationProvider;
	
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

	/**
	 * @return the informationProvider
	 */
	public InformationProvider getInformationProvider() {
		return i_informationProvider;
	}

	/**
	 * @param informationProvider the informationProvider to set
	 */
	public void setInformationProvider(InformationProvider informationProvider) {
		i_informationProvider = informationProvider;
	}
}
