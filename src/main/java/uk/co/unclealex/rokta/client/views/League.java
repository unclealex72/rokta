package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.LeaguePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.formatters.ArrowFormat;
import com.google.gwt.visualization.client.formatters.NumberFormat;
import com.google.gwt.visualization.client.formatters.NumberFormat.Options;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.inject.Provider;

public class League extends Composite implements Display {

  @UiTemplate("League.ui.xml")
	public interface Binder extends UiBinder<Widget, League> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasOneWidget league;
	@UiField HasText exemptPlayer;
	
	public League() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(final Provider<DataTable> dataTableProvider) {
		Runnable leagueDrawer = new Runnable() {
			@Override
			public void run() {
				DataTable leagueDataTable = dataTableProvider.get();
				ArrowFormat.create(ArrowFormat.Options.create()).format(leagueDataTable, 0);
				formatNumber(leagueDataTable, 5, null);
				formatNumber(leagueDataTable, 6, null);
				formatNumber(leagueDataTable, 7, "%");
				Table league = new Table();
				getLeague().setWidget(league);
				league.draw(leagueDataTable);
			}
			
			protected void formatNumber(DataTable dataTable, int column, String suffix) {
				Options options = NumberFormat.Options.create();
				if (suffix != null) {
					options.setSuffix(suffix);
				}
				NumberFormat.create(options).format(dataTable, column);
			}
		};
		VisualizationUtils.loadVisualizationApi(leagueDrawer, Table.PACKAGE);
	}

	public HasOneWidget getLeague() {
		return league;
	}

	public HasText getExemptPlayer() {
		return exemptPlayer;
	}
	
}
