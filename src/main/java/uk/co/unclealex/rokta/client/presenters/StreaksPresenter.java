package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.factories.StreaksTablePresenterFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Streak;
import uk.co.unclealex.rokta.shared.model.Streaks;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class StreaksPresenter extends InformationPresenter<Streaks> {

	public static interface Display extends IsWidget {

		HasOneWidget getAllStreaksPanel();
		HasOneWidget getCurrentStreaksPanel();
	}
	
	private final StreaksTablePresenterFactory i_streaksTablePresenterFactory;
	private final Display i_display;

	public StreaksPresenter(GameFilter gameFilter, InformationCache informationCache,
			StreaksTablePresenterFactory streaksTablePresenterFactory, Display display) {
		super(gameFilter, informationCache);
		i_streaksTablePresenterFactory = streaksTablePresenterFactory;
		i_display = display;
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
		StreaksTablePresenter allStreaksTablePresenter = 
				streaksTablePresenterFactory.createStreaksTablePresenter(getAllStreaks(streaks));
		allStreaksTablePresenter.show(display.getAllStreaksPanel());
		StreaksTablePresenter currentStreaksTablePresenter = 
				streaksTablePresenterFactory.createStreaksTablePresenter(getCurrentStreaks(streaks));
		currentStreaksTablePresenter.show(display.getCurrentStreaksPanel());
	}
	
	protected abstract SortedSet<Streak> getAllStreaks(Streaks streaks);
	protected abstract SortedSet<Streak> getCurrentStreaks(Streaks streaks);
	
	public Display getDisplay() {
		return i_display;
	}

	public StreaksTablePresenterFactory getStreaksTablePresenterFactory() {
		return i_streaksTablePresenterFactory;
	}
}
