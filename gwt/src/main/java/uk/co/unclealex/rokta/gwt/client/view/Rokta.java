package uk.co.unclealex.rokta.gwt.client.view;

import java.util.Date;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.listener.LoadingListener;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.view.decoration.CopyrightPanel;
import uk.co.unclealex.rokta.gwt.client.view.decoration.TitlePanel;
import uk.co.unclealex.rokta.gwt.client.view.factory.DecorationFactory;
import uk.co.unclealex.rokta.gwt.client.view.factory.MainPanelFactory;
import uk.co.unclealex.rokta.gwt.client.view.factory.SidePanelFactory;
import uk.co.unclealex.rokta.gwt.client.view.main.MainPanel;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This application demonstrates how to construct a relatively complex user
 * interface, similar to many common email readers. It has no back-end,
 * populating its components with hard-coded data.
 */
public class Rokta implements EntryPoint, LoadingListener<String>, AsyncCallback<List<String>> {

  /**
   * Instantiate an application-level image bundle. This object will provide
   * programmatic access to all the images needed by widgets.
   */
  private static final Images IMAGES = GWT.create(Images.class);

  /**
   * An aggragate image bundle that pulls together all the images for this
   * application into a single bundle.
   */
  public interface Images extends TitlePanel.Images {
  }

  private GwtReadOnlyRoktaFacadeAsync i_gwtReadOnlyRoktaFacadeAsync;
  
  /**
   * This method constructs the application user interface by instantiating
   * controls and hooking up event listeners.
   */
  public void onModuleLoad() {
		GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync = 
			(GwtReadOnlyRoktaFacadeAsync) GWT.create(GwtReadOnlyRoktaFacade.class);
		ServiceDefTarget target = (ServiceDefTarget) gwtReadOnlyRoktaFacadeAsync;
		target.setServiceEntryPoint("../handler/rpc");
		setGwtReadOnlyRoktaFacadeAsync(gwtReadOnlyRoktaFacadeAsync);
  	gwtReadOnlyRoktaFacadeAsync.getAllPlayerNames(this);
  }
  
  public void onFailure(Throwable caught) {
  	Window.alert("Could not get a list of players from the server. Please try again.");
  }
  
  public void onSuccess(List<String> playerNames) {
  	RoktaModel roktaModel = new RoktaModel(playerNames);
  	RoktaController roktaController =
  		new RoktaController(roktaModel, new YearGameFilter(new Date()), getGwtReadOnlyRoktaFacadeAsync());
  	DecorationFactory decorationFactory = new DecorationFactory(roktaController, roktaModel);
  	SidePanelFactory sidePanelFactory = new SidePanelFactory(roktaController, roktaModel);
  	MainPanelFactory mainPanelFactory = new MainPanelFactory(roktaController, roktaModel);
    DockPanel outerPanel = new DockPanel();
    TitlePanel titlePanel = decorationFactory.createTitlePanel(IMAGES);
		outerPanel.add(titlePanel, DockPanel.NORTH);
    VerticalPanel sidePanel = new VerticalPanel();
    sidePanel.add(sidePanelFactory.createNavigationPanel());
    sidePanel.add(sidePanelFactory.createDetailPanel(playerNames));
    outerPanel.add(sidePanel, DockPanel.WEST);
		MainPanel mainPanel =
			mainPanelFactory.createMainPanel();
		outerPanel.add(mainPanel, DockPanel.CENTER);
    CopyrightPanel copyrightPanel = decorationFactory.createCopyrightPanel();
		outerPanel.add(copyrightPanel, DockPanel.SOUTH);

  	for (Widget widget : new Widget[] { titlePanel, copyrightPanel, outerPanel }) {
  		widget.setWidth("100%");
  	}

    outerPanel.setSpacing(4);
    outerPanel.setCellWidth(mainPanel, "100%");
    // Finally, add the outer panel to the RootPanel, so that it will be
    // displayed.
    RootPanel.get().add(outerPanel);
    roktaModel.getTitleModel().addListener(this);
    roktaController.start();
  }

  public void onLoading() {
  	Window.setTitle("ROKTA");
  }
  
  public void onValueChanged(String title) {
  	Window.setTitle("ROKTA - " + title);
  }
  
  public void onLoaded() {
  	// Do nothing
  }

	public GwtReadOnlyRoktaFacadeAsync getGwtReadOnlyRoktaFacadeAsync() {
		return i_gwtReadOnlyRoktaFacadeAsync;
	}

	public void setGwtReadOnlyRoktaFacadeAsync(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync) {
		i_gwtReadOnlyRoktaFacadeAsync = gwtReadOnlyRoktaFacadeAsync;
	}
}
