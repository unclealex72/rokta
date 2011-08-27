package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.factories.StreaksTablePresenterFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Streak;
import uk.co.unclealex.rokta.shared.model.Streaks;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class StreaksPresenter extends InformationPresenter<Streaks> {

	public static interface Display extends IsWidget {

		HasText getAllStreaksTitle();
		HasOneWidget getAllStreaksPanel();
		HasText getCurrentStreaksTitle();
		HasOneWidget getCurrentStreaksPanel();
	}
	
	private final StreaksTablePresenterFactory i_streaksTablePresenterFactory;
	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	public StreaksPresenter(GameFilter gameFilter, InformationCache informationCache,
			StreaksTablePresenterFactory streaksTablePresenterFactory, Display display, TitleMessages titleMessages) {
		super(gameFilter, informationCache);
		i_streaksTablePresenterFactory = streaksTablePresenterFactory;
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	protected Streaks asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getStreaks();
	}
	
	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, Streaks streaks) {
		Display display = getDisplay();
		panel.setWidget(display);
		StreaksTablePresenterFactory streaksTablePresenterFactory = getStreaksTablePresenterFactory();
		SortedSet<Streak> allStreaks = getAllStreaks(streaks);
		display.getAllStreaksTitle().setText(createAllStreaksTitle(getTitleMessages(), allStreaks));
		StreaksTablePresenter allStreaksTablePresenter = 
				streaksTablePresenterFactory.createStreaksTablePresenter(allStreaks);
		allStreaksTablePresenter.show(display.getAllStreaksPanel());
		SortedSet<Streak> currentStreaks = getCurrentStreaks(streaks);
		display.getCurrentStreaksTitle().setText(createCurrentStreaksTitle(getTitleMessages(), currentStreaks));
		StreaksTablePresenter currentStreaksTablePresenter = 
				streaksTablePresenterFactory.createStreaksTablePresenter(currentStreaks);
		currentStreaksTablePresenter.show(display.getCurrentStreaksPanel());
	}
	
	protected abstract String createCurrentStreaksTitle(TitleMessages titleMessages, SortedSet<Streak> currentStreaks);
	protected abstract String createAllStreaksTitle(TitleMessages titleMessages, SortedSet<Streak> allStreaks);
	protected abstract SortedSet<Streak> getAllStreaks(Streaks streaks);
	protected abstract SortedSet<Streak> getCurrentStreaks(Streaks streaks);
	
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
