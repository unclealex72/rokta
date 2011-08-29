package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.NewGamePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class NewGame extends Composite implements Display {

  @UiTemplate("NewGame.ui.xml")
	public interface Binder extends UiBinder<Widget, NewGame> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasText exemptPlayer;
	@UiField ListBox instigatorListBox;
	@UiField ListBox playersListBox;
	@UiField Button startButton;

	@Inject
	public NewGame() {
		initWidget(binder.createAndBindUi(this));
	}
	
	@UiFactory
	public ListBox createListBox(boolean multiple) {
		return new ListBox(multiple);
	}
	
	public HasText getExemptPlayer() {
		return exemptPlayer;
	}

	public ListBox getInstigatorListBox() {
		return instigatorListBox;
	}

	public ListBox getPlayersListBox() {
		return playersListBox;
	}

	public Button getStartButton() {
		return startButton;
	}
	
}