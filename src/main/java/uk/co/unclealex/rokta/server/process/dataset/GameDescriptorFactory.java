package uk.co.unclealex.rokta.server.process.dataset;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.quotient.DatePlayedQuotientTransformer;

public interface GameDescriptorFactory {

	public Transformer<Game, String> createGameDescriptor(DatePlayedQuotientTransformer datePlayedQuotientTransformer);

}
