package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.StreaksModel;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;
import uk.co.unclealex.rokta.pub.views.Streak;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;

public class StreaksWidget extends LoadingAwareComposite<StreaksLeague, Grid> {

	private Grid i_grid = new Grid();
	private StreaksMessages i_messages = GWT.create(StreaksMessages.class);

	public StreaksWidget(RoktaController roktaController, StreaksModel streaksModel) {
		super(roktaController, streaksModel);
	}

	@Override
	protected Grid create() {
		return getGrid();
	}
	
	public void onValueChanged(StreaksLeague streaksLeague) {
		int size = streaksLeague.getSize();
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
	
	protected Grid getGrid() {
		return i_grid;
	}

	public StreaksMessages getMessages() {
		return i_messages;
	}
}
