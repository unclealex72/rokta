/**
 * 
 */
package uk.co.unclealex.rokta.internal.process.dataset;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

/**
 * @author alex
 *
 */
public interface LeagueGraphDatasetProducer extends DatasetProducer<ColourCategoryDataset> {

	public void initialise(GameFilter gameFilter, int maximumNumberOfResults, DateTime currentDate);

}