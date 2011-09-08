package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.factories.HandCountPresenterFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.ProfilePresenter.Display;
import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.PlayerProfile;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public class ProfilePresenter extends InformationActivity<Display, PlayerProfile> {

	public static interface Display extends IsWidget {
		AcceptsOneWidget getHandCountPanel();
		AcceptsOneWidget getOpeningHandCountPanel();
		HasText getColourTitle();
		void setColour(Colour colour);
	}

	@Inject
	public ProfilePresenter(@Assisted GameFilter gameFilter, InformationService informationService, @Assisted String username, Display display,
			HandCountPresenterFactory handCountPresenterFactory, TitleMessages titleMessages) {
		super(gameFilter, informationService);
		i_username = username;
		i_display = display;
		i_handCountPresenterFactory = handCountPresenterFactory;
		i_titleMessages = titleMessages;
	}

	private final String i_username;
	private final Display i_display;
	private final HandCountPresenterFactory i_handCountPresenterFactory;
	private final TitleMessages i_titleMessages;
	
	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, PlayerProfile playerProfile) {
		Display display = getDisplay();
		panel.setWidget(display);
		String username = getUsername();
		TitleMessages titleMessages = getTitleMessages();
		HandCountPresenterFactory handCountPresenterFactory = getHandCountPresenterFactory();
		HandCountPresenter handCountPresenter = 
				handCountPresenterFactory.createHandCountPresenter(
						titleMessages.handCounts(username), playerProfile.getHandCounts());
		HandCountPresenter openingHandCountPresenter = 
				handCountPresenterFactory.createHandCountPresenter(
						titleMessages.openingHandCounts(username), playerProfile.getOpeningHandCounts());
		handCountPresenter.show(display.getHandCountPanel());
		openingHandCountPresenter.show(display.getOpeningHandCountPanel());
		display.getColourTitle().setText(titleMessages.playerColour(username));
		display.setColour(playerProfile.getColour());
	}
	
	@Override
	protected PlayerProfile asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getPlayerProfilesByName().get(getUsername());
	}


	public String getUsername() {
		return i_username;
	}

	public Display getDisplay() {
		return i_display;
	}

	public HandCountPresenterFactory getHandCountPresenterFactory() {
		return i_handCountPresenterFactory;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
}
