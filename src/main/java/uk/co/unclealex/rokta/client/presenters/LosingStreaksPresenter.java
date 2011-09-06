package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.inject.assistedinject.Assisted;

public class LosingStreaksPresenter extends StreaksPresenter {

	@Inject
	public LosingStreaksPresenter(@Assisted GameFilter gameFilter, InformationService informationService,
			Display display, TitleMessages titleMessages) {
		super(gameFilter, informationService, display, titleMessages);
	}

	@Override
	protected String createStreaksTitle(TitleMessages titleMessages, SortedSet<Streak> allStreaks) {
		return titleMessages.losingStreaksSubtitle(allStreaks.size());
	}
	
	@Override
	protected SortedSet<Streak> asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getLosingStreaks();
	}
}
