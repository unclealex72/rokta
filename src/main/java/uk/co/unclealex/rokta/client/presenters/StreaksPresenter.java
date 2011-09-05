package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.factories.StreaksTablePresenterFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.StreaksPresenter.Display;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class StreaksPresenter extends InformationActivity<Display, SortedSet<Streak>> {

	public static interface Display extends IsWidget {

		HasText getAllStreaksTitle();
		HasOneWidget getAllStreaksPanel();
	}
	
	private final StreaksTablePresenterFactory i_streaksTablePresenterFactory;
	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	public StreaksPresenter(GameFilter gameFilter, InformationService informationService,
			StreaksTablePresenterFactory streaksTablePresenterFactory, Display display, TitleMessages titleMessages) {
		super(gameFilter, informationService);
		i_streaksTablePresenterFactory = streaksTablePresenterFactory;
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, SortedSet<Streak> streaks) {
		Display display = getDisplay();
		panel.setWidget(display);
		StreaksTablePresenterFactory streaksTablePresenterFactory = getStreaksTablePresenterFactory();
		display.getAllStreaksTitle().setText(createStreaksTitle(getTitleMessages(), streaks));
		StreaksTablePresenter allStreaksTablePresenter = 
				streaksTablePresenterFactory.createStreaksTablePresenter(streaks);
		allStreaksTablePresenter.show(display.getAllStreaksPanel());
	}
	
	protected abstract String createStreaksTitle(TitleMessages titleMessages, SortedSet<Streak> allStreaks);
	
	public Display getDisplay() {
		return i_display;
	}

	public StreaksTablePresenterFactory getStreaksTablePresenterFactory() {
		return i_streaksTablePresenterFactory;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
}
