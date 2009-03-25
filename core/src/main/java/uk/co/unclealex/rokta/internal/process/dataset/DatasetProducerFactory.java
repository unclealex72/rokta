package uk.co.unclealex.rokta.internal.process.dataset;

import org.jfree.data.general.PieDataset;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.model.Person;

public interface DatasetProducerFactory {

	public DatasetProducer<ColourCategoryDataset> createLeagueGraphDatasetProducer(
			GameFilter gameFilter, int maximumNumberOfResults, DateTime currentDate);
	
	public DatasetProducer<PieDataset> createHandDistributionDatasetProducer(GameFilter gameFilter, Person person);
	
	public DatasetProducer<PieDataset> createOpeningGambitHandDistributionDatasetProducer(GameFilter gameFilter, Person person);
}
