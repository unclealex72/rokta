package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public class ProfilePresenter extends AbstractGameFilterActivity {

	public static interface Display extends IsWidget {

	}

	private final String i_username;
	
	@Inject
	public ProfilePresenter(@Assisted GameFilter gameFilter, @Assisted String username) {
		super(gameFilter);
		i_username = username;
	}

	@Override
	public void start(GameFilter gameFilter, AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		
	}

	public String getUsername() {
		return i_username;
	}

}
