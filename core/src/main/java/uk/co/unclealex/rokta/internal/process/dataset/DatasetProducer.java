package uk.co.unclealex.rokta.process.dataset;

import org.jfree.data.general.Dataset;

/**
 * An interface for classes that create chart datasets.
 * @author alex
 *
 * @param <D>
 */
public interface DatasetProducer<D extends Dataset> {

	public D produceDataset();

}
