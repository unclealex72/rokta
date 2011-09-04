package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.views.TableDisplay;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.assistedinject.Assisted;

public class StreaksTablePresenter implements Presenter {

	public static interface Display extends TableDisplay {
		String HEADER = "header";
	}

	private final Display i_display;
	private final SortedSet<Streak> i_streaks;
	
	@Inject
	public StreaksTablePresenter(Display display, @Assisted SortedSet<Streak> streaks) {
		i_display = display;
		i_streaks = streaks;
	}


	@Override
	public void show(AcceptsOneWidget container) {
		Display display = getDisplay();
		container.setWidget(display);
		display.draw(createStreaksTable(), null, null);
	}

	public Table createStreaksTable() {
		Table streaksTable = new Table();
		streaksTable.addRow(Display.HEADER, "Rank", "Player", "Games", "From", "To");
		for (Streak streak : getStreaks()) {
			streaksTable.addRow(
					null, streak.getRank(), streak.getPersonName(), streak.getLength(), streak.getStartDate(), streak.getEndDate());
		}
		return streaksTable;
	}
	
	public SortedSet<Streak> getStreaks() {
		return i_streaks;
	}


	public Display getDisplay() {
		return i_display;
	}
}
