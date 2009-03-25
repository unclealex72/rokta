/**
 * 
 */
package uk.co.unclealex.rokta.internal.process.dataset;

import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;

import uk.co.unclealex.rokta.pub.model.Person;

/**
 * @author alex
 *
 */
public class GamesLostDatasetProducer extends StatisticsAwareDatasetProducer<DefaultPieDataset> {

	public DefaultPieDataset produceDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<Person,Integer> entry : getStatisticsManager().countGamesLostByPlayer().entrySet()) {
			dataset.setValue(entry.getKey().getName(), entry.getValue());
		}
		return dataset;
	}

}
