/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.Date;
import java.util.Map;

import uk.co.unclealex.rokta.process.InformationProvider;
import uk.co.unclealex.rokta.process.StatisticsManager;
import de.laures.cewolf.DatasetProducer;

/**
 * @author alex
 *
 */
public abstract class StatisticsAwareDatasetProducer implements DatasetProducer {

	private StatisticsManager i_statisticsManager;
	private InformationProvider i_informationProvider;
	
	public final String getProducerId() {
		return getClass().getName();
	}

	public boolean hasExpired(Map params, Date since) {
		return true;
	}

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
