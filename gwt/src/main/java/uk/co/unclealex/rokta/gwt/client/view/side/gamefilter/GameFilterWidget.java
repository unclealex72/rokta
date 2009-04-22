package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.view.side.HeaderElement;
import uk.co.unclealex.rokta.gwt.client.view.side.NavigationMessages;
import uk.co.unclealex.rokta.pub.filter.AndGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GameFilterWidget extends RoktaAwareComposite implements GameFilterProducerListener, ClickHandler {

	private ContinuousGameFilterWidget i_continuousGameFilterWidget;
	private NonContinuousGameFilterWidget i_nonContinuousGameFilterWidget;
	private Button i_button;
	
	public GameFilterWidget(RoktaController roktaController, InitialDatesModel model) {
		super(roktaController);
		NavigationMessages navigationMessages = GWT.create(NavigationMessages.class);
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HeaderElement(navigationMessages.filters(), 1));
		ContinuousGameFilterWidget continuousGameFilterWidget = new ContinuousGameFilterWidget(roktaController, model, this); 
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = new NonContinuousGameFilterWidget(roktaController, model, this);
		Button button = new Button("Change", this);
		button.setEnabled(false);
		setContinuousGameFilterWidget(continuousGameFilterWidget);
		setNonContinuousGameFilterWidget(nonContinuousGameFilterWidget);
		verticalPanel.add(continuousGameFilterWidget);
		verticalPanel.add(nonContinuousGameFilterWidget);
		verticalPanel.add(button);
		initWidget(verticalPanel);
	}

	public void onGameFilterProduced(GameFilterProducerEvent gameFilterProducerEvent) {
		Button button = getButton();
		if (button != null) {
			button.setEnabled(getGameFilter() != null);
		}
	}
	
	public void onClick(ClickEvent event) {
		GameFilter gameFilter = getGameFilter();
		if (gameFilter != null) {
			getRoktaController().changeGameFilter(gameFilter);
		}
	}
	
	public GameFilter getGameFilter() {
		GameFilter gameFilter = null;
		ContinuousGameFilterWidget continuousGameFilterWidget = getContinuousGameFilterWidget();
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = getNonContinuousGameFilterWidget();
		if (continuousGameFilterWidget != null && nonContinuousGameFilterWidget != null) {
			GameFilter continuousGameFilter = continuousGameFilterWidget.getGameFilter();
			if (continuousGameFilter != null) {
				GameFilter nonContinuousGameFilter = nonContinuousGameFilterWidget.getGameFilter();
				if (nonContinuousGameFilter != null) {
					gameFilter = new AndGameFilter(continuousGameFilter, nonContinuousGameFilter);
				}
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
