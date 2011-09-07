package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.GameFinishedPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class GameFinished extends Composite implements Display {

  @UiTemplate("GameFinished.ui.xml")
	public interface Binder extends UiBinder<Widget, GameFinished> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasText loser;
	@UiField HasClickHandlers submitButton;
	@UiField HasClickHandlers backButton;
	
	@Inject
	public GameFinished() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasText getLoser() {
		return loser;
	}

	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	@Override
	public HasClickHandlers getBackButton() {
		return backButton;
	}
}
