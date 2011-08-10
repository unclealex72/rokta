package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public class MainPresenter implements Presenter {

	public static interface Display extends IsWidget {
		
	}
	
	private final Display i_display;
	
	@Inject
	public MainPresenter(Display display) {
		super();
		i_display = display;
	}

	@Override
	public void show(HasWidgets container) {
		container.clear();
		container.add(getDisplay().asWidget());
	}
	
	public Display getDisplay() {
		return i_display;
	}

}
