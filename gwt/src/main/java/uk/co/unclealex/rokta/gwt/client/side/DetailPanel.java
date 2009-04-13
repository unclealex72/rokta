package uk.co.unclealex.rokta.gwt.client.side;

import uk.co.unclealex.rokta.gwt.client.DetailPanelChanger;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;

import com.google.gwt.user.client.ui.DeckPanel;

public class DetailPanel extends RoktaAwareComposite implements DetailPanelChanger {

	private DeckPanel i_deckPanel;
	
	public DetailPanel(String id, RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		DeckPanel deckPanel = new DeckPanel();
		deckPanel.add(new GameFiltersPanel(roktaAdaptor));
		deckPanel.add(new ProfilesPanel(roktaAdaptor));
		deckPanel.add(new StatisticsPanel(roktaAdaptor));
		setDeckPanel(deckPanel);
		initWidget(id, deckPanel);
	}

	public void showFilters() {
		getDeckPanel().showWidget(0);
	}

	public void showProfiles() {
		getDeckPanel().showWidget(1);
	}

	public void showStatistics() {
		getDeckPanel().showWidget(2);
	}

	public DeckPanel getDeckPanel() {
		return i_deckPanel;
	}

	public void setDeckPanel(DeckPanel deckPanel) {
		i_deckPanel = deckPanel;
	}

}
