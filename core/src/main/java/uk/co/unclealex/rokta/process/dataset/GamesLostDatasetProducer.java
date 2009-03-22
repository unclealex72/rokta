/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;

import uk.co.unclealex.rokta.model.Person;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author alex
 *
 */
public class GamesLostDatasetProducer extends StatisticsAwareDatasetProducer {

	public Object produceDataset(Map params) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<Person,Integer> entry : getStatisticsManager().countGamesLostByPlayer().entrySet()) {
			dataset.setValue(entry.getKey().getName(), entry.getValue());
		}
		return dataset;
	}

}
