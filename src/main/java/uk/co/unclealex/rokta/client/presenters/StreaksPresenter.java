package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.StreaksPresenter.Display;
import uk.co.unclealex.rokta.client.views.TableDisplay;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;

public abstract class StreaksPresenter extends InformationActivity<Display, SortedSet<Streak>> {

	public static interface Display extends TableDisplay {
		HasText getStreaksTitle();
	}
	
	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	public StreaksPresenter(GameFilter gameFilter, InformationService informationService,
			Display display, TitleMessages titleMessages) {
		super(gameFilter, informationService);
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, SortedSet<Streak> streaks) {
		Display display = getDisplay();
		panel.setWidget(display);
		display.getStreaksTitle().setText(createStreaksTitle(getTitleMessages(), streaks));
		display.draw(createStreaksTable(streaks), null, null);
	}
	
	public Table createStreaksTable(SortedSet<Streak> streaks) {
		Table streaksTable = new Table();
		streaksTable.addRow(Display.HEADER, "Rank", "Player", "Games", "From", "To");
		for (Streak streak : streaks) {
			streaksTable.addRow(
					null, streak.getRank(), streak.getPersonName(), streak.getLength(), streak.getStartDate(), streak.getEndDate());
		}
		return streaksTable;
	}

	protected abstract String createStreaksTitle(TitleMessages titleMessages, SortedSet<Streak> allStreaks);
	
	public Display getDisplay() {
		return i_display;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
}
