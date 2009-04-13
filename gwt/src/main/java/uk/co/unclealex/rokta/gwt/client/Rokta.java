package uk.co.unclealex.rokta.gwt.client;

import uk.co.unclealex.rokta.gwt.client.decoration.CopyrightPanel;
import uk.co.unclealex.rokta.gwt.client.decoration.TitlePanel;
import uk.co.unclealex.rokta.gwt.client.main.MainPanel;
import uk.co.unclealex.rokta.gwt.client.side.DetailPanel;
import uk.co.unclealex.rokta.gwt.client.side.NavigationPanel;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This application demonstrates how to construct a relatively complex user
 * interface, similar to many common email readers. It has no back-end,
 * populating its components with hard-coded data.
 */
public class Rokta implements EntryPoint, GameFilterListener, MainPanelChanger, DetailPanelChanger {

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

  private String i_title;
  private RoktaAdaptor i_roktaAdaptor;
  private TitlePanel i_titlePanel;
  private MainPanel i_mainPanel;
  private NavigationPanel i_navigationBox;
  private CopyrightPanel i_copyrightPanel;
  private DetailPanel i_detailPanel;
  /**
   * This method constructs the application user interface by instantiating
   * controls and hooking up event listeners.
   */
  public void onModuleLoad() {
  	final RoktaAdaptor roktaAdaptor = new RoktaAdaptorImpl(this, this);
  	setRoktaAdaptor(roktaAdaptor);
  	roktaAdaptor.addGameFilterListener(this);
  	NavigationPanel navigationBox = new NavigationPanel("navigationPanel", roktaAdaptor);
  	TitlePanel titlePanel = new TitlePanel("titlePanel", roktaAdaptor, IMAGES);
  	CopyrightPanel copyrightPanel = new CopyrightPanel("copyrightPanel", roktaAdaptor);
  	MainPanel mainPanel = new MainPanel("mainPanel", roktaAdaptor);
  	DetailPanel detailPanel = new DetailPanel("detailPanel", roktaAdaptor);
  	setTitlePanel(titlePanel);
  	setMainPanel(mainPanel);
  	setNavigationBox(navigationBox);
  	setDetailPanel(detailPanel);
  	
    DockPanel outerPanel = new DockPanel();
    outerPanel.add(titlePanel, DockPanel.NORTH);
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(navigationBox);
    verticalPanel.add(detailPanel);
    outerPanel.add(verticalPanel, DockPanel.WEST);
    outerPanel.add(mainPanel, DockPanel.CENTER);
    outerPanel.add(copyrightPanel, DockPanel.SOUTH);

  	for (Widget widget : new Widget[] { titlePanel, copyrightPanel, outerPanel }) {
  		widget.setWidth("100%");
  	}

    outerPanel.setSpacing(4);
    outerPanel.setCellWidth(mainPanel, "100%");
    // Finally, add the outer panel to the RootPanel, so that it will be
    // displayed.
    RootPanel.get().add(outerPanel);
    roktaAdaptor.populateDefaults();
    showLeague();
  }

  public void onGameFilterChange(GameFilter newGameFilter) {
  	updateTitle();
  }

  public void updateTitle() {
  	AsyncCallback<String> callback = new DefaultAsyncCallback<String>() {
  		public void onSuccess(String title) {
  			Window.setTitle(title);
  		}
  	};
  	getRoktaAdaptor().getTitle(getTitle(), callback);
  }

  public String updateTitle(String newTitle) {
  	setTitle(newTitle);
  	updateTitle();
  	return newTitle;
  }
  
  public String showLeague() {
  	return updateTitle(getMainPanel().showLeague());
  }
  
  public String showLosingStreaks() {
  	return updateTitle(getMainPanel().showLosingStreaks());
  }
  
  public String showWinningStreaks() {
  	return updateTitle(getMainPanel().showWinningStreaks());
  }

  public String showHeadToHeads() {
  	return updateTitle(getMainPanel().showHeadToHeads());
  }
  
  public String showProfile(String personName) {
  	return updateTitle(getMainPanel().showProfile(personName));
  }
  
  public void startNewGame() {
  	getMainPanel().startNewGame();
  }
  
  public void showFilters() {
  	getDetailPanel().showFilters();
  }
  
  public void showProfiles() {
  	getDetailPanel().showProfiles();
  }
  
  public void showStatistics() {
  	getDetailPanel().showStatistics();
  }
  
	public TitlePanel getTitlePanel() {
		return i_titlePanel;
	}

	public void setTitlePanel(TitlePanel titlePanel) {
		i_titlePanel = titlePanel;
	}

	public MainPanel getMainPanel() {
		return i_mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		i_mainPanel = mainPanel;
	}

	public CopyrightPanel getCopyrightPanel() {
		return i_copyrightPanel;
	}

	public void setCopyrightPanel(CopyrightPanel copyrightPanel) {
		i_copyrightPanel = copyrightPanel;
	}

	public RoktaAdaptor getRoktaAdaptor() {
		return i_roktaAdaptor;
	}

	public void setRoktaAdaptor(RoktaAdaptor roktaAdaptor) {
		i_roktaAdaptor = roktaAdaptor;
	}

	public DetailPanel getDetailPanel() {
		return i_detailPanel;
	}

	public void setDetailPanel(DetailPanel detailPanel) {
		i_detailPanel = detailPanel;
	}

	public NavigationPanel getNavigationBox() {
		return i_navigationBox;
	}

	public void setNavigationBox(NavigationPanel navigationBox) {
		i_navigationBox = navigationBox;
	}

	public String getTitle() {
		return i_title;
	}

	public void setTitle(String title) {
		i_title = title;
	}
}
