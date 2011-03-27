package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.view.side.HeaderElement;
import uk.co.unclealex.rokta.gwt.client.view.side.NavigationMessages;
import uk.co.unclealex.rokta.pub.filter.AndGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GameFilterWidget extends RoktaAwareComposite<VerticalPanel> implements GameFilterProducerListener, ClickListener {

	private ContinuousGameFilterWidget i_continuousGameFilterWidget;
	private NonContinuousGameFilterWidget i_nonContinuousGameFilterWidget;
	private Button i_button;
	
	public GameFilterWidget(
			RoktaController roktaController, InitialDatesModel model, 
			ContinuousGameFilterWidget continuousGameFilterWidget, NonContinuousGameFilterWidget nonContinuousGameFilterWidget) {
		super(roktaController);
		i_continuousGameFilterWidget = continuousGameFilterWidget;
		i_nonContinuousGameFilterWidget = nonContinuousGameFilterWidget;
	}

	@Override
	protected VerticalPanel create() {
		NavigationMessages navigationMessages = GWT.create(NavigationMessages.class);
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HeaderElement(navigationMessages.filters(), 1));
		Button button = new Button("Change", this);
		button.setEnabled(false);
		setButton(button);
		verticalPanel.add(getContinuousGameFilterWidget());
		verticalPanel.add(getNonContinuousGameFilterWidget());
		verticalPanel.add(button);
		return verticalPanel;
	}
	
	public void onGameFilterProduced(GameFilterProducerEvent gameFilterProducerEvent) {
		Button button = getButton();
		if (button != null) {
			button.setEnabled(getGameFilter() != null);
		}
	}
	
	public void onClick(Widget source) {
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
				else {
					gameFilter = continuousGameFilter;
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
