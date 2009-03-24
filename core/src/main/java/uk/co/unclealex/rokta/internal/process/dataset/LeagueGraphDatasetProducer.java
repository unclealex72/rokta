/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.filter.GameFilter;

/**
 * @author alex
 *
 */
public interface LeagueGraphDatasetProducer extends DatasetProducer<ColourCategoryDataset> {

	public void initialise(GameFilter gameFilter, int maximumNumberOfResults, DateTime currentDate);

}