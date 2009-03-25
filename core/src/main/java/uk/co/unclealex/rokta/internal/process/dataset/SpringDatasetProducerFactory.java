package uk.co.unclealex.rokta.internal.process.dataset;

import org.jfree.data.general.PieDataset;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.spring.ApplicationContextAwareSupport;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class SpringDatasetProducerFactory extends ApplicationContextAwareSupport implements DatasetProducerFactory {

	@Override
	public DatasetProducer<ColourCategoryDataset> createLeagueGraphDatasetProducer(
			GameFilter gameFilter, int maximumNumberOfResults, DateTime currentDate) {
		LeagueGraphDatasetProducer datasetProducer = getBean("leagueGraphDatasetProducer", LeagueGraphDatasetProducer.class);
		datasetProducer.initialise(gameFilter, maximumNumberOfResults, currentDate);
		return datasetProducer;
	}

	@Override
	public DatasetProducer<PieDataset> createHandDistributionDatasetProducer(GameFilter gameFilter, Person person) {
		 datasetProducer = getBean("leagueGraphDatasetProducer", LeagueGraphDatasetProducer.class);
		datasetProducer.initialise(gameFilter, maximumNumberOfResults, currentDate);
	}

	@Override
	public DatasetProducer<PieDataset> createOpeningGambitHandDistributionDatasetProducer(GameFilter gameFilter,
			Person person) {
		// TODO Auto-generated method stub
		return null;
	}

}
