package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.pub.filter.AndGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GameFilterWidget extends RoktaAwareComposite implements GameFilterProducerListener, ClickHandler {

	private ContinuousGameFilterWidget i_continuousGameFilterWidget;
	private NonContinuousGameFilterWidget i_nonContinuousGameFilterWidget;
	private Button i_button;
	
	public GameFilterWidget(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		VerticalPanel verticalPanel = new VerticalPanel();
		ContinuousGameFilterWidget continuousGameFilterWidget = new ContinuousGameFilterWidget(roktaAdaptor, this); 
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = new NonContinuousGameFilterWidget(roktaAdaptor, this);
		Button button = new Button("Change", this);
		button.setEnabled(false);
		setContinuousGameFilterWidget(continuousGameFilterWidget);
		setNonContinuousGameFilterWidget(nonContinuousGameFilterWidget);
		verticalPanel.add(continuousGameFilterWidget);
		verticalPanel.add(nonContinuousGameFilterWidget);
	}

	public void onGameFilterProduced(GameFilterProducerEvent gameFilterProducerEvent) {
		getButton().setEnabled(getGameFilter() != null);
	}
	
	public void onClick(ClickEvent event) {
		GameFilter gameFilter = getGameFilter();
		if (gameFilter != null) {
			getRoktaAdaptor().changeGameFilter(gameFilter);
		}
	}
	
	public GameFilter getGameFilter() {
		GameFilter gameFilter = null;
		GameFilter continuousGameFilter = getContinuousGameFilterWidget().getGameFilter();
		if (continuousGameFilter != null) {
			GameFilter nonContinuousGameFilter = getNonContinuousGameFilterWidget().getGameFilter();
			if (nonContinuousGameFilter != null) {
				gameFilter = new AndGameFilter(continuousGameFilter, nonContinuousGameFilter);
			}
		}
		return gameFilter;
	}
	
	public ContinuousGameFilterWidget getContinuousGameFilterWidget() {
		return i_continuousGameFilterWidget;
	}

	public void setContinuousGameFilterWidget(ContinuousGameFilterWidget continuousGameFilterWidget) {
		i_continuousGameFilterWidget = continuousGameFilterWidget;
	}

	public NonContinuousGameFilterWidget getNonContinuousGameFilterWidget() {
		return i_nonContinuousGameFilterWidget;
	}

	public void setNonContinuousGameFilterWidget(NonContinuousGameFilterWidget nonContinuousGameFilterWidget) {
		i_nonContinuousGameFilterWidget = nonContinuousGameFilterWidget;
	}

	public Button getButton() {
		return i_button;
	}

	public void setButton(Button button) {
		i_button = button;
	}
}
