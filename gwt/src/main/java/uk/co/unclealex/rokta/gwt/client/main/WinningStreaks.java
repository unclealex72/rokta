package uk.co.unclealex.rokta.gwt.client.main;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.side.NavigationMessages;
import uk.co.unclealex.rokta.gwt.client.side.StatisticsMessages;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class WinningStreaks extends Streaks {

	protected WinningStreaks(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
	}

	@Override
	protected void doStreaksCallback(RoktaAdaptor roktaAdaptor, int targetSize, AsyncCallback<StreaksLeague> callback) {
		roktaAdaptor.getWinningStreaks(targetSize, callback);
	}

	@Override
	protected String createTitle(NavigationMessages navigationMessages, StatisticsMessages statisticsMessages) {
		return statisticsMessages.winningStreaks();
	}
}
