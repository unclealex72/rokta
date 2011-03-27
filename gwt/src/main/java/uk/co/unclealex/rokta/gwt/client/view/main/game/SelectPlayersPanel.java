package uk.co.unclealex.rokta.gwt.client.view.main.game;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.gwt.client.view.date.DateFormatter;
import uk.co.unclealex.rokta.gwt.client.view.date.DatePicker;
import uk.co.unclealex.rokta.gwt.client.view.date.DatePickerNames;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Game.State;

import com.google.code.p.gwtchismes.client.GWTCDatePicker;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectPlayersPanel extends AbstractGamePanel<VerticalPanel> {

	private Label i_exemptPlayerLabel;
	private ListBox i_playerSelectionListBox;
	private ListBox i_instigatorListBox;
	private DatePicker i_datePicker;
	private int i_playerCount;
	
	public SelectPlayersPanel(RoktaController roktaController, GameModel gameModel) {
		super(roktaController, gameModel);
	}

	@Override
	protected VerticalPanel createTopWidget() {
		VerticalPanel panel = new VerticalPanel();
		setExemptPlayerLabel(new Label());
		setInstigatorListBox(new ListBox(false));
		setPlayerSelectionListBox(new ListBox(true));
		setDatePicker(
			new DatePicker(
					GWTCDatePicker.CONFIG_DIALOG | GWTCDatePicker.CONFIG_NO_HELP_BUTTON | 
					GWTCDatePicker.CONFIG_BACKGROUND | GWTCDatePicker.CONFIG_LAYOUT_1,
					new DateFormatter() {
						public String prefix() {
							return DatePickerNames.getInstance().datePlayed();
						}
						public String formatDate(Date date) {
							return DatePickerNames.getInstance().datePlayedFormat(date);
						}
					}));
		return panel;
	}

	public void doOnValueChanged(Game game) {
		final String exemptPlayer = game.getExemptPlayer();
		final GameMessages gameMessages = getGameMessages();
		getExemptPlayerLabel().setText(
				exemptPlayer==null?gameMessages.nooneExempt():gameMessages.exempt(exemptPlayer));
		ListBox playerSelectionListBox = getPlayerSelectionListBox();
		while (playerSelectionListBox.getItemCount() != 0) {
			playerSelectionListBox.removeItem(0);
		}
		int playerCount = 0;
		for (String player : game.getAllBarExemptPlayers()) {
			playerSelectionListBox.addItem(player);
			playerCount++;
		}
		setPlayerCount(playerCount);
		ListBox instigatorListBox = getInstigatorListBox();
		while (instigatorListBox.getItemCount() != 0) {
			instigatorListBox.removeItem(0);
		}
		for (String user : game.getAllUsers()) {
			instigatorListBox.addItem(user);
		}
		getDatePicker().setSelectedDate(new Date());
	}
	
	@Override
	protected State[] getStatesToListenFor() {
		return new State[] { State.CHOOSE_PLAYERS };
	}
	
	@Override
	protected void next() {
		ListBox instigatorListBox = getInstigatorListBox();
		SortedSet<String> players = new TreeSet<String>();
		final ListBox playerSelectionListBox = getPlayerSelectionListBox();
		int playerCount = getPlayerCount();
		for (int idx = 0; idx < playerCount; idx++) {
			if (playerSelectionListBox.isItemSelected(idx)) {
				players.add(playerSelectionListBox.getItemText(idx));
			}
		}
		getRoktaGameController().selectPlayers(
				instigatorListBox.getItemText(instigatorListBox.getSelectedIndex()),
				players,
				getDatePicker().getSelectedDate());
	}
	
	@Override
	protected boolean showBackButton() {
		return false;
	}
	
	protected Label getExemptPlayerLabel() {
		return i_exemptPlayerLabel;
	}

	protected void setExemptPlayerLabel(Label exemptPlayerLabel) {
		i_exemptPlayerLabel = exemptPlayerLabel;
	}

	protected ListBox getPlayerSelectionListBox() {
		return i_playerSelectionListBox;
	}

	protected void setPlayerSelectionListBox(ListBox playerSelectionListBox) {
		i_playerSelectionListBox = playerSelectionListBox;
	}

	protected ListBox getInstigatorListBox() {
		return i_instigatorListBox;
	}

	protected void setInstigatorListBox(ListBox instigatorListBox) {
		i_instigatorListBox = instigatorListBox;
	}

	protected DatePicker getDatePicker() {
		return i_datePicker;
	}

	protected void setDatePicker(DatePicker datePicker) {
		i_datePicker = datePicker;
	}

	public int getPlayerCount() {
		return i_playerCount;
	}

	public void setPlayerCount(int playerCount) {
		i_playerCount = playerCount;
	}
}
