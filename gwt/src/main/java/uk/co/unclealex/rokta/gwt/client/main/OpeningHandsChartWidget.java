package uk.co.unclealex.rokta.gwt.client.main;

import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.views.Hand;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class OpeningHandsChartWidget extends AbstractHandsChartWidget {

	public OpeningHandsChartWidget(RoktaAdaptor roktaAdaptor, String playerName) {
		super(roktaAdaptor, playerName);
	}

	@Override
	protected String getChartTitle() {
		return "Opening Hands";
	}

	@Override
	protected void executeChartCallback(AsyncCallback<Map<Hand, Integer>> callback) {
		getRoktaAdaptor().createOpeningHandDistributionGraph(getPlayerName(), callback);
	}

}
