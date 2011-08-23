package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public class MainPresenter implements Presenter {

	public static interface Display extends IsWidget {

		HasOneWidget getTitlePanel();
		HasOneWidget getMainPanel();
		public abstract HasOneWidget getNavigationPanel();
		
	}
	
	private final Display i_display;
	
	@Inject
	public MainPresenter(Display display) {
		super();
		i_display = display;
	}

	@Override
	public void show(AcceptsOneWidget container) {
		container.setWidget(getDisplay());
	}
	
	public Display getDisplay() {
		return i_display;
	}

}
