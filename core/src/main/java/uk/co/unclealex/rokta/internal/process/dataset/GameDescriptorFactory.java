package uk.co.unclealex.rokta.internal.process.dataset;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.quotient.DatePlayedQuotientTransformer;

public interface GameDescriptorFactory {

	public Transformer<Game, String> createGameDescriptor(DatePlayedQuotientTransformer datePlayedQuotientTransformer);

}
