package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.NextRoundPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class NextRound extends Composite implements Display {

  @UiTemplate("NextRound.ui.xml")
	public interface Binder extends UiBinder<Widget, NextRound> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField ListBox counterListBox;
	@UiField Button nextButton;
	@UiField FlexTable playersTable;
	
	@Inject
	public NextRound() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void addPlayer(String playerName, ListBox handListBox) {
		FlexTable playersTable = getPlayersTable();
		int row = playersTable.getRowCount();
		playersTable.setText(row, 0, playerName);
		playersTable.setWidget(row, 1, handListBox);
	}

	public ListBox getCounterListBox() {
		return counterListBox;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public FlexTable getPlayersTable() {
		return playersTable;
	}

}
