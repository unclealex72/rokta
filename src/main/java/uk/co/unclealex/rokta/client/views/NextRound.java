package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.NextRoundPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class NextRound extends Composite implements Display {

	public interface Style extends CssResource {
		String player();
	}
  @UiTemplate("NextRound.ui.xml")
	public interface Binder extends UiBinder<Widget, NextRound> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField Style style;
	@UiField ListBox counterListBox;
	@UiField Button nextButton;
	@UiField Button backButton;
	@UiField HasWidgets playersPanel;
	
	@Inject
	public NextRound() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void addPlayer(String playerName, ListBox handListBox) {
		HasWidgets playersPanel = getPlayersPanel();
		FlowPanel innerPanel = new FlowPanel();
		Label playerLabel = new Label(playerName);
		playerLabel.addStyleName(getStyle().player());
		for (Widget w : new Widget[] { playerLabel, handListBox }) {
			innerPanel.add(w);
		}
		playersPanel.add(innerPanel);
	}

	public ListBox getCounterListBox() {
		return counterListBox;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public HasWidgets getPlayersPanel() {
		return playersPanel;
	}

	public Style getStyle() {
		return style;
	}

	public Button getBackButton() {
		return backButton;
	}

}
