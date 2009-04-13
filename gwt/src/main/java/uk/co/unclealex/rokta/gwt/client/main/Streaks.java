package uk.co.unclealex.rokta.gwt.client.main;

import uk.co.unclealex.rokta.gwt.client.ErrorHandler;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Streak;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;

public abstract class Streaks extends MainPanelComposite implements AsyncCallback<StreaksLeague> {

	private int i_streakCount;
	private Grid i_grid = new Grid();
	private StreaksMessages i_messages = GWT.create(StreaksMessages.class);

	protected Streaks(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		initWidget(getGrid());
	}

	public void onGameFilterChange(GameFilter newGameFilter) {
		doStreaksCallback(getRoktaAdaptor(), 10, this);
	}

	public void onFailure(Throwable t) {
		ErrorHandler.log(t);
	}
	
	public void onSuccess(StreaksLeague streaksLeague) {
		int size = streaksLeague.getSize();
		setStreakCount(size);
		StreaksMessages messages = getMessages();
		Grid grid = getGrid();
		grid.resize(size + 1, 5);
		String[] headers = new String[] { 
			messages.rankTitle(), messages.playerTitle(), messages.lengthTitle(), 
			messages.fromTitle(), messages.toTitle() };
		for (int column = 0; column < headers.length; column++) {
			grid.setText(0, column, headers[column]);
		}
		int row = 0;
		for (Streak streak : streaksLeague.getStreaks()) {
			row++;
			int rank = streak.getRank();
			String[] data =
				new String[] { 
					streak.isRankSameAsPreviousRank()?"=":messages.rankFormat(rank),
					messages.playerFormat(streak.getPersonName()), messages.lengthFormat(streak.getLength()),
					messages.fromFormat(streak.getStartDate()), messages.toFormat(streak.getEndDate()),
			};
			for (int column = 0; column < data.length; column++) {
				grid.setText(row, column, data[column]);
			}
		}
	}
	
	protected abstract void doStreaksCallback(RoktaAdaptor roktaAdaptor, int targetSize, AsyncCallback<StreaksLeague> callback);

	public int getStreakCount() {
		return i_streakCount;
	}

	protected void setStreakCount(int streakCount) {
		i_streakCount = streakCount;
	}

	public Grid getGrid() {
		return i_grid;
	}

	public StreaksMessages getMessages() {
		return i_messages;
	}
}
