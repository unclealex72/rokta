/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Hand;

/**
 * @author alex
 *
 */
public class HandChoiceDatasetProducer extends HandCountingDatasetProducer {

	@Override
	public SortedMap<Hand, Integer> getHandCount() {
		return getProfileManager().countHands();
	}

}
