package uk.co.unclealex.rokta.server.process.dataset;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.collections15.Transformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.InstantDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.YearDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.visitor.DatePlayedQuotientTransformerVisitor;

@Service
@Transactional
public class GameDescriptorFactoryImpl extends DatePlayedQuotientTransformerVisitor<String> implements GameDescriptorFactory {

	private ResourceBundle i_resourceBundle;
	
	@Override
	public Transformer<Game, String> createGameDescriptor(DatePlayedQuotientTransformer datePlayedQuotientTransformer) {
		String resourceKey = datePlayedQuotientTransformer.accept(this);
		final String message = getResourceBundle().getString("quotient." + resourceKey);
		Transformer<Game, String> transformer = new Transformer<Game, String>() {
			@Override
			public String transform(Game game) {
				return MessageFormat.format(message, game.getDatePlayed());
			}
		};
		return transformer;
	}

	@Override
	public String visit(DayDatePlayedQuotientTransformer dayDatePlayedQuotientTransformer) {
		return "day";
	}
	
	@Override
	public String visit(WeekDatePlayedQuotientTransformer weekDatePlayedQuotientTransformer) {
		return "week";
	}
	
	@Override
	public String visit(MonthDatePlayedQuotientTransformer monthDatePlayedQuotientTransformer) {
		return "month";
	}
	
	@Override
	public String visit(YearDatePlayedQuotientTransformer yearDatePlayedQuotientTransformer) {
		return "year";
	}	

	@Override
	public String visit(InstantDatePlayedQuotientTransformer instantDatePlayedQuotientTransformer) {
		return "instant";
	}
	
	public ResourceBundle getResourceBundle() {
		return i_resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		i_resourceBundle = resourceBundle;
	}

}
