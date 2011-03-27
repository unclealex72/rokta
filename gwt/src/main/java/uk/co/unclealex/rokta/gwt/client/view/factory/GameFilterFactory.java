package uk.co.unclealex.rokta.gwt.client.view.factory;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.AllGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.BeforeGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.BetweenGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.ContinuousGameFilterWidget;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.GameFilterProducerComposite;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.GameFilterProducerListener;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.GameFilterWidget;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.LastFourWeeksGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.MonthGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.NonContinuousGameFilterWidget;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.SinceGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.ThisMonthGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.ThisWeekGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.ThisYearGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.TodayGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.WeekGameFilterProducerPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.YearGameFilterProducerPanel;

public class GameFilterFactory extends ViewFactory {

	public GameFilterFactory(RoktaController roktaController, RoktaModel roktaModel) {
		super(roktaController, roktaModel);
	}

	public GameFilterWidget createGameFilterWidget() {
		ContinuousGameFilterWidget continuousGameFilterWidget = createContinuousGameFilterWidget();
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = createNonContinuousGameFilterWidget();
		GameFilterWidget gameFilterWidget = 
			new GameFilterWidget(
					getRoktaController(), getRoktaModel().getInitialDatesModel(), continuousGameFilterWidget, nonContinuousGameFilterWidget);
		addListeners(gameFilterWidget, continuousGameFilterWidget, nonContinuousGameFilterWidget);
		gameFilterWidget.initialise();
		return gameFilterWidget;
	}

	protected ContinuousGameFilterWidget createContinuousGameFilterWidget() {
		RoktaController roktaController = getRoktaController();
		InitialDatesModel model = getRoktaModel().getInitialDatesModel();
		ThisYearGameFilterProducerPanel thisYearGameFilterProducerPanel = new ThisYearGameFilterProducerPanel(roktaController, model);
		LastFourWeeksGameFilterProducerPanel lastFourWeeksGameFilterProducerPanel = new LastFourWeeksGameFilterProducerPanel(roktaController, model);
		ThisMonthGameFilterProducerPanel thisMonthGameFilterProducerPanel = new ThisMonthGameFilterProducerPanel(roktaController, model);
		ThisWeekGameFilterProducerPanel thisWeekGameFilterProducerPanel = new ThisWeekGameFilterProducerPanel(roktaController, model);
		TodayGameFilterProducerPanel todayGameFilterProducerPanel = new TodayGameFilterProducerPanel(roktaController, model);
		YearGameFilterProducerPanel yearGameFilterProducerPanel = new YearGameFilterProducerPanel(roktaController, model);
		MonthGameFilterProducerPanel monthGameFilterProducerPanel = new MonthGameFilterProducerPanel(roktaController, model);
		WeekGameFilterProducerPanel weekGameFilterProducerPanel = new WeekGameFilterProducerPanel(roktaController, model);
		BeforeGameFilterProducerPanel beforeGameFilterProducerPanel = new BeforeGameFilterProducerPanel(roktaController, model);
		BetweenGameFilterProducerPanel betweenGameFilterProducerPanel = new BetweenGameFilterProducerPanel(roktaController, model);
		SinceGameFilterProducerPanel sinceGameFilterProducerPanel = new SinceGameFilterProducerPanel(roktaController, model);
		AllGameFilterProducerPanel allGameFilterProducerPanel = new AllGameFilterProducerPanel(roktaController, model);
		ContinuousGameFilterWidget continuousGameFilterWidget = new ContinuousGameFilterWidget(
				roktaController, model, yearGameFilterProducerPanel, monthGameFilterProducerPanel, weekGameFilterProducerPanel, 
				beforeGameFilterProducerPanel, betweenGameFilterProducerPanel, sinceGameFilterProducerPanel, allGameFilterProducerPanel, 
				thisYearGameFilterProducerPanel, lastFourWeeksGameFilterProducerPanel, thisMonthGameFilterProducerPanel, 
				thisWeekGameFilterProducerPanel, todayGameFilterProducerPanel);
		addListeners(continuousGameFilterWidget, yearGameFilterProducerPanel, monthGameFilterProducerPanel, weekGameFilterProducerPanel, 
				beforeGameFilterProducerPanel, betweenGameFilterProducerPanel, sinceGameFilterProducerPanel, allGameFilterProducerPanel, 
				thisYearGameFilterProducerPanel, lastFourWeeksGameFilterProducerPanel, thisMonthGameFilterProducerPanel, 
				thisWeekGameFilterProducerPanel, todayGameFilterProducerPanel);
		batchInitialise(yearGameFilterProducerPanel, monthGameFilterProducerPanel, weekGameFilterProducerPanel, 
				beforeGameFilterProducerPanel, betweenGameFilterProducerPanel, sinceGameFilterProducerPanel, allGameFilterProducerPanel, 
				thisYearGameFilterProducerPanel, lastFourWeeksGameFilterProducerPanel, thisMonthGameFilterProducerPanel, 
				thisWeekGameFilterProducerPanel, todayGameFilterProducerPanel, continuousGameFilterWidget);
		return continuousGameFilterWidget;
	}
	protected NonContinuousGameFilterWidget createNonContinuousGameFilterWidget() {
		NonContinuousGameFilterWidget nonContinuousGameFilterWidget = 
			new NonContinuousGameFilterWidget(getRoktaController(), getRoktaModel().getInitialDatesModel());
		nonContinuousGameFilterWidget.initialise();
		return nonContinuousGameFilterWidget;
	}

	protected void addListeners(GameFilterProducerListener gameFilterProducerListener, GameFilterProducerComposite<?>... composites) {
		for (GameFilterProducerComposite<?> composite : composites) {
			composite.addGameFilterProducerListener(gameFilterProducerListener);
		}
	}
	
	protected void batchInitialise(GameFilterProducerComposite<?>... composites) {
		for (GameFilterProducerComposite<?> composite : composites) {
			composite.initialise();
		}
	}

}
