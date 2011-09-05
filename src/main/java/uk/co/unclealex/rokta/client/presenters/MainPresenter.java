package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.MainPresenter.Display;
import uk.co.unclealex.rokta.client.ui.HidingDockLayoutPanel;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public class MainPresenter implements Presenter<Display> {

	public static interface Display extends IsWidget {

		HasOneWidget getTitlePanel();
		HasOneWidget getMainPanel();
		HasOneWidget getNavigationPanel();
		AcceptsOneWidget getAuthenticationPanel();
		HidingDockLayoutPanel getDock();
		HasOneWidget getNewsPanel();
	}
	
	private final Display i_display;
	
	@Inject
	public MainPresenter(Display display) {
		super();
		i_display = display;
	}

	interface DockAction {
		public void execute(HidingDockLayoutPanel dock, Direction direction);
	}
	
	public void makeWide(final Runnable action) {
		DockAction dockAction = new DockAction() {
			@Override
			public void execute(HidingDockLayoutPanel dock, Direction direction) {
				dock.hide(direction, action);
			}
		};
		executeInDock(dockAction);
	}

	public void makeNarrow(final Runnable action) {
		DockAction dockAction = new DockAction() {
			@Override
			public void execute(HidingDockLayoutPanel dock, Direction direction) {
				dock.show(direction, action);
			}
		};
		executeInDock(dockAction);
	}


	protected void executeInDock(DockAction dockAction) {
		dockAction.execute(getDisplay().getDock(), Direction.EAST);
	}

	@Override
	public void show(AcceptsOneWidget container) {
		container.setWidget(getDisplay());
	}
	
	public Display getDisplay() {
		return i_display;
	}

}
