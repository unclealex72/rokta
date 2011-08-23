package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.GamePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class Game extends Composite implements Display {

  @UiTemplate("Game.ui.xml")
	public interface Binder extends UiBinder<Widget, Game> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasOneWidget mainPanel;
	
	public Game() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasOneWidget getMainPanel() {
		return mainPanel;
	}

}
