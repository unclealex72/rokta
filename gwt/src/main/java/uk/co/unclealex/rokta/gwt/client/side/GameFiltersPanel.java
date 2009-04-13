package uk.co.unclealex.rokta.gwt.client.side;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.ContinuousGameFilterWidget;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.GameFilterMessages;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.GameFilterProducer;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.GameFilterProducerEvent;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.GameFilterProducerListener;
import uk.co.unclealex.rokta.gwt.client.side.gamefilter.NonContinuousGameFilterWidget;
import uk.co.unclealex.rokta.pub.filter.AndGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GameFiltersPanel extends RoktaAwareComposite implements GameFilterProducerListener, ClickHandler {

	private GameFilterProducer i_continuousGameFilterProducer;
	private GameFilterProducer i_nonContinuousGameFilterProducer;
	private GameFilter i_gameFilter;
	private Button i_button;
	
	public GameFiltersPanel(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		NavigationMessages navigationMessages = GWT.create(NavigationMessages.class);
		GameFilterMessages gameFilterMessages = GWT.create(GameFilterMessages.class);
		VerticalPanel panel = new VerticalPanel();
		panel.add(new HeaderElement(navigationMessages.filters(), 1));
		ContinuousGameFilterWidget continuousGameFilterWidget = new ContinuousGameFilterWidget(roktaAdaptor, this);
		setContinuousGameFilterProducer(continuousGameFilterWidget);
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = new NonContinuousGameFilterWidget(roktaAdaptor);
		setNonContinuousGameFilterProducer(nonContinuousGameFilterWidget);
		continuousGameFilterWidget.addGameFilterProducerListener(this);
		nonContinuousGameFilterWidget.addGameFilterProducerListener(this);
		Button button = new Button(gameFilterMessages.ok(), this);
		setButton(button);
		panel.add(continuousGameFilterWidget);
		panel.add(nonContinuousGameFilterWidget);
		panel.add(button);
		panel.setCellHorizontalAlignment(button, VerticalPanel.ALIGN_RIGHT);
		onGameFilterProduced(null);
		initWidget(panel);
	}

	public void onClick(ClickEvent event) {
		getRoktaAdaptor().changeGameFilter(getGameFilter());
	}
	
	public void onGameFilterProduced(GameFilterProducerEvent gameFilterProducerEvent) {
		GameFilter gameFilter = createGameFilter();
		setGameFilter(gameFilter);
		getButton().setEnabled(gameFilter != null);
	}
	
	protected GameFilter createGameFilter() {
		GameFilter continuousGameFilter = getContinuousGameFilterProducer().getGameFilter();
		GameFilter nonContinuousGameFilter = getNonContinuousGameFilterProducer().getGameFilter();
		if (continuousGameFilter != null) {
			if (nonContinuousGameFilter != null) {
				return new AndGameFilter(continuousGameFilter, nonContinuousGameFilter);
			}
			else {
				return continuousGameFilter;
			}
		}
		else {
			return null;
		}
	}
	
	public GameFilterProducer getNonContinuousGameFilterProducer() {
		return i_nonContinuousGameFilterProducer;
	}

	public void setNonContinuousGameFilterProducer(GameFilterProducer nonContinuousGameFilterProducer) {
		i_nonContinuousGameFilterProducer = nonContinuousGameFilterProducer;
	}

	public GameFilterProducer getContinuousGameFilterProducer() {
		return i_continuousGameFilterProducer;
	}

	public void setContinuousGameFilterProducer(GameFilterProducer continuousGameFilterProducer) {
		i_continuousGameFilterProducer = continuousGameFilterProducer;
	}

	public Button getButton() {
		return i_button;
	}

	public void setButton(Button button) {
		i_button = button;
	}

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}

	public void setGameFilter(GameFilter gameFilter) {
		i_gameFilter = gameFilter;
	}

}
