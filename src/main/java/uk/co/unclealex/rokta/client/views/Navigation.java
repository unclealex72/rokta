package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.NavigationPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class Navigation extends Composite implements Display {

  @UiTemplate("Navigation.ui.xml")
	public interface Binder extends UiBinder<Widget, Navigation> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	// Links
	@UiField HasClickHandlers newGameLink;
	@UiField HasClickHandlers leagueLink;
	@UiField HasClickHandlers graphLink;
	@UiField HasClickHandlers winningStreaksLink;
	@UiField HasClickHandlers losingStreaksLink;
	@UiField HasClickHandlers headToHeadsLink;
	@UiField HasClickHandlers adminLink;
	@UiField HasClickHandlers filtersLink;
	@UiField HasWidgets profilesPanel;
	
	@Inject
	public Navigation() {
		initWidget(binder.createAndBindUi(this));
	}
	
	@Override
	public HasClickHandlers addProfileLink(String playerName) {
		Anchor anchor = createAnchor();
		anchor.setText(playerName);
		getProfilesPanel().add(anchor);
		return anchor;
	}
	
	@UiFactory
	public Anchor createAnchor() {
		return new Anchor(true);
	}

	public HasClickHandlers getNewGameLink() {
		return newGameLink;
	}

	public HasClickHandlers getFiltersLink() {
		return filtersLink;
	}

	public HasClickHandlers getLeagueLink() {
		return leagueLink;
	}

	public HasClickHandlers getGraphLink() {
		return graphLink;
	}

	public HasClickHandlers getWinningStreaksLink() {
		return winningStreaksLink;
	}

	public HasClickHandlers getLosingStreaksLink() {
		return losingStreaksLink;
	}

	public HasClickHandlers getHeadToHeadsLink() {
		return headToHeadsLink;
	}

	public HasClickHandlers getAdminLink() {
		return adminLink;
	}

	public HasWidgets getProfilesPanel() {
		return profilesPanel;
	}

}
