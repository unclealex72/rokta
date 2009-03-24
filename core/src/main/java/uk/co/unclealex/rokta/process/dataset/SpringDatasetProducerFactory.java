package uk.co.unclealex.rokta.process.dataset;

import org.jfree.data.general.PieDataset;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.spring.ApplicationContextAwareSupport;

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
