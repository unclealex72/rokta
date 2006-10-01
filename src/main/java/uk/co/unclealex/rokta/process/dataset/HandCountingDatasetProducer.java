/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.Map;
import java.util.SortedMap;

import org.jfree.data.general.DefaultPieDataset;

import uk.co.unclealex.rokta.model.Hand;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author alex
 *
 */
public abstract class HandCountingDatasetProducer extends ProfileAwareDatasetProducer {

	public Object produceDataset(Map params) throws DatasetProduceException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		SortedMap<Hand, Integer> handCounts = getHandCount();
		int sum = 0;
		for (int count : handCounts.values()) {
			sum += count;
		}
		for (Map.Entry<Hand,Integer> entry : handCounts.entrySet()) {
			dataset.setValue(entry.getKey().getDescription(), 100.0 * entry.getValue() / sum);
		}
		return dataset;

		
	}

	/**
	 * @return
	 */
	public abstract SortedMap<Hand, Integer> getHandCount();

}
