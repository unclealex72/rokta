/**
 * 
 */
package uk.co.unclealex.rokta.internal.process.dataset;

import java.util.Map;
import java.util.SortedMap;

import org.jfree.data.general.DefaultPieDataset;

import uk.co.unclealex.rokta.internal.model.Hand;

/**
 * @author alex
 *
 */
public class HandCountingDatasetProducer extends StatisticsAwareDatasetProducer<DefaultPieDataset> {

	private SortedMap<Hand, Integer> i_handCount;
	
	public DefaultPieDataset produceDataset() {
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

	public SortedMap<Hand, Integer> getHandCount() {
		return i_handCount;
	}

	public void setHandCount(SortedMap<Hand, Integer> handCount) {
		i_handCount = handCount;
	}

}
