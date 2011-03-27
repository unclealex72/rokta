package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilterVistor;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContinuousGameFilterWidget extends GameFilterProducerComposite<VerticalPanel> 
	implements GameFilterProducer, GameFilterProducerListener, ChangeListener {

	private DeckPanel i_panel;
	private ListBox i_listBox;
	
	private YearGameFilterProducerPanel i_yearGameFilterProducerPanel;
	private MonthGameFilterProducerPanel i_monthGameFilterProducerPanel;
	private WeekGameFilterProducerPanel i_weekGameFilterProducerPanel;
	private BeforeGameFilterProducerPanel i_beforeGameFilterProducerPanel;
	private BetweenGameFilterProducerPanel i_betweenGameFilterProducerPanel;
	private SinceGameFilterProducerPanel i_sinceGameFilterProducerPanel;
	private AllGameFilterProducerPanel i_allGameFilterProducerPanel;
	private ThisYearGameFilterProducerPanel i_thisYearGameFilterProducerPanel;
	private LastFourWeeksGameFilterProducerPanel i_lastFourWeeksGameFilterProducerPanel;
	private ThisMonthGameFilterProducerPanel i_thisMonthGameFilterProducerPanel;
	private ThisWeekGameFilterProducerPanel i_thisWeekGameFilterProducerPanel;
	private TodayGameFilterProducerPanel i_todayGameFilterProducerPanel;

	public ContinuousGameFilterWidget(
			RoktaController roktaController, InitialDatesModel model,
			YearGameFilterProducerPanel yearGameFilterProducerPanel,
			MonthGameFilterProducerPanel monthGameFilterProducerPanel,
			WeekGameFilterProducerPanel weekGameFilterProducerPanel,
			BeforeGameFilterProducerPanel beforeGameFilterProducerPanel,
			BetweenGameFilterProducerPanel betweenGameFilterProducerPanel,
			SinceGameFilterProducerPanel sinceGameFilterProducerPanel,
			AllGameFilterProducerPanel allGameFilterProducerPanel,
			ThisYearGameFilterProducerPanel thisYearGameFilterProducerPanel,
			LastFourWeeksGameFilterProducerPanel lastFourWeeksGameFilterProducerPanel,
			ThisMonthGameFilterProducerPanel thisMonthGameFilterProducerPanel,
			ThisWeekGameFilterProducerPanel thisWeekGameFilterProducerPanel,
			TodayGameFilterProducerPanel todayGameFilterProducerPanel) {
		super(roktaController, model);
		i_yearGameFilterProducerPanel = yearGameFilterProducerPanel;
		i_monthGameFilterProducerPanel = monthGameFilterProducerPanel;
		i_weekGameFilterProducerPanel = weekGameFilterProducerPanel;
		i_beforeGameFilterProducerPanel = beforeGameFilterProducerPanel;
		i_betweenGameFilterProducerPanel = betweenGameFilterProducerPanel;
		i_sinceGameFilterProducerPanel = sinceGameFilterProducerPanel;
		i_allGameFilterProducerPanel = allGameFilterProducerPanel;
		i_thisYearGameFilterProducerPanel = thisYearGameFilterProducerPanel;
		i_lastFourWeeksGameFilterProducerPanel = lastFourWeeksGameFilterProducerPanel;
		i_thisMonthGameFilterProducerPanel = thisMonthGameFilterProducerPanel;
		i_thisWeekGameFilterProducerPanel = thisWeekGameFilterProducerPanel;
		i_todayGameFilterProducerPanel = todayGameFilterProducerPanel;
	}

	@Override
	protected VerticalPanel create() {
		VerticalPanel verticalPanel = new VerticalPanel();
		ListBox listBox = new ListBox();
		setListBox(listBox);
		listBox.addChangeListener(this);
		final DeckPanel panel = new DeckPanel();
		setPanel(panel);

		GameFilterMessages messages = GWT.create(GameFilterMessages.class);

		add(getThisYearGameFilterProducerPanel(), messages.thisYear());
		add(getLastFourWeeksGameFilterProducerPanel(), messages.lastFourWeeks());
		add(getThisMonthGameFilterProducerPanel(), messages.thisMonth());
		add(getThisWeekGameFilterProducerPanel(), messages.thisWeek());
		add(getTodayGameFilterProducerPanel(), messages.today());
		add(getYearGameFilterProducerPanel(), messages.year());
		add(getMonthGameFilterProducerPanel(), messages.month());
		add(getWeekGameFilterProducerPanel(), messages.week());
		add(getBeforeGameFilterProducerPanel(), messages.before());
		add(getBetweenGameFilterProducerPanel(), messages.between());
		add(getSinceGameFilterProducerPanel(), messages.since());
		add(getAllGameFilterProducerPanel(), messages.all());
		
		getListBox().setSelectedIndex(0);
		panel.showWidget(0);
		setGameFilter(createGameFilter());
		verticalPanel.add(listBox);
		verticalPanel.add(panel);
		return verticalPanel;
	}
	
	protected void add(
			GameFilterProducerComposite<?> gameFilterProducerComposite, String title) {
		getPanel().add(gameFilterProducerComposite);
		getListBox().addItem(title);
	}

	public void onChange(Widget source) {
		int index = getListBox().getSelectedIndex();
		getPanel().showWidget(index);
	}
	
	public void onGameFilterProduced(GameFilterProducerEvent gameFilterProducerEvent) {
		setGameFilter(gameFilterProducerEvent.getGameFilter());
	}
	
	@Override
	protected GameFilter createGameFilter() {
		int index = getPanel().getVisibleWidget();
		return ((GameFilterProducer) getPanel().getWidget(index)).getGameFilter();
	}
	
	protected class ContinuousGameFilterVisitor extends GameFilterVistor<Object> {

		protected Object select(GameFilterProducerComposite<?> gameFilterProducerPanel) {
			DeckPanel panel = getPanel();
			int widgetIndex = panel.getWidgetIndex(gameFilterProducerPanel);
			panel.showWidget(widgetIndex);
			return null;
		}
		
		@Override
		public Object join(Object leftResult, Object rightResult) {
			return null;
		}

		@Override
		public Object visit(BeforeGameFilter beforeGameFilter) {
			BeforeGameFilterProducerPanel beforeGameFilterProducerPanel = getBeforeGameFilterProducerPanel();
			beforeGameFilterProducerPanel.setBeforeDate(beforeGameFilter.getBefore());
			return select(beforeGameFilterProducerPanel);
		}

		@Override
		public Object visit(BetweenGameFilter betweenGameFilter) {
			BetweenGameFilterProducerPanel betweenGameFilterProducerPanel = getBetweenGameFilterProducerPanel();
			betweenGameFilterProducerPanel.setFromDate(betweenGameFilter.getFrom());
			betweenGameFilterProducerPanel.setToDate(betweenGameFilter.getTo());
			return select(betweenGameFilterProducerPanel);
		}

		@Override
		public Object visit(SinceGameFilter sinceGameFilter) {
			SinceGameFilterProducerPanel sinceGameFilterProducerPanel = getSinceGameFilterProducerPanel();
			sinceGameFilterProducerPanel.setSinceDate(sinceGameFilter.getSince());
			return select(sinceGameFilterProducerPanel);
		}

		@Override
		public Object visit(MonthGameFilter monthGameFilter) {
			MonthGameFilterProducerPanel monthGameFilterProducerPanel = getMonthGameFilterProducerPanel();
			monthGameFilterProducerPanel.updateYearAndMonth(monthGameFilter.getDate());
			return select(monthGameFilterProducerPanel);
		}

		@Override
		public Object visit(WeekGameFilter weekGameFilter) {
			WeekGameFilterProducerPanel weekGameFilterProducerPanel = getWeekGameFilterProducerPanel();
			weekGameFilterProducerPanel.updateYearAndWeek(weekGameFilter.getDate());
			return select(weekGameFilterProducerPanel);
		}

		@SuppressWarnings("deprecation")
		@Override
		public Object visit(YearGameFilter yearGameFilter) {
			YearGameFilterProducerPanel yearGameFilterProducerPanel = getYearGameFilterProducerPanel();
			yearGameFilterProducerPanel.setYear(yearGameFilter.getDate().getYear() + 1900);
			return select(yearGameFilterProducerPanel);
		}

		@Override
		public Object visit(AllGameFilter allGameFilter) {
			return select(getAllGameFilterProducerPanel());
		}

		@Override
		public Object visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter) {
			return null;
		}

		@Override
		public Object visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter) {
			return null;
		}

		@Override
		public Object visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter) {
			return null;
		}

		@Override
		public Object visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter) {
			return null;
		}

		@Override
		public Object visit(LastGameOfTheDayFilter lastGameOfTheDayFilter) {
			return null;
		}

		@Override
		public Object visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter) {
			return null;
		}

		@Override
		public Object visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter) {
			return null;
		}

		@Override
		public Object visit(LastGameOfTheYearFilter lastGameOfTheYearFilter) {
			return null;
		}		
	}

	public void onValueChanged(InitialDatesView value) {
		// Do nothing
	}
	
	protected YearGameFilterProducerPanel getYearGameFilterProducerPanel() {
		return i_yearGameFilterProducerPanel;
	}

	protected void setYearGameFilterProducerPanel(YearGameFilterProducerPanel yearGameFilterProducerPanel) {
		i_yearGameFilterProducerPanel = yearGameFilterProducerPanel;
	}

	protected MonthGameFilterProducerPanel getMonthGameFilterProducerPanel() {
		return i_monthGameFilterProducerPanel;
	}

	protected void setMonthGameFilterProducerPanel(MonthGameFilterProducerPanel monthGameFilterProducerPanel) {
		i_monthGameFilterProducerPanel = monthGameFilterProducerPanel;
	}

	protected WeekGameFilterProducerPanel getWeekGameFilterProducerPanel() {
		return i_weekGameFilterProducerPanel;
	}

	protected void setWeekGameFilterProducerPanel(WeekGameFilterProducerPanel weekGameFilterProducerPanel) {
		i_weekGameFilterProducerPanel = weekGameFilterProducerPanel;
	}

	protected BeforeGameFilterProducerPanel getBeforeGameFilterProducerPanel() {
		return i_beforeGameFilterProducerPanel;
	}

	protected void setBeforeGameFilterProducerPanel(BeforeGameFilterProducerPanel beforeGameFilterProducerPanel) {
		i_beforeGameFilterProducerPanel = beforeGameFilterProducerPanel;
	}

	protected BetweenGameFilterProducerPanel getBetweenGameFilterProducerPanel() {
		return i_betweenGameFilterProducerPanel;
	}

	protected void setBetweenGameFilterProducerPanel(BetweenGameFilterProducerPanel betweenGameFilterProducerPanel) {
		i_betweenGameFilterProducerPanel = betweenGameFilterProducerPanel;
	}

	protected SinceGameFilterProducerPanel getSinceGameFilterProducerPanel() {
		return i_sinceGameFilterProducerPanel;
	}

	protected void setSinceGameFilterProducerPanel(SinceGameFilterProducerPanel sinceGameFilterProducerPanel) {
		i_sinceGameFilterProducerPanel = sinceGameFilterProducerPanel;
	}

	protected AllGameFilterProducerPanel getAllGameFilterProducerPanel() {
		return i_allGameFilterProducerPanel;
	}

	protected void setAllGameFilterProducerPanel(AllGameFilterProducerPanel allGameFilterProducerPanel) {
		i_allGameFilterProducerPanel = allGameFilterProducerPanel;
	}

	public DeckPanel getPanel() {
		return i_panel;
	}

	public void setPanel(DeckPanel panel) {
		i_panel = panel;
	}

	public ListBox getListBox() {
		return i_listBox;
	}

	public void setListBox(ListBox listBox) {
		i_listBox = listBox;
	}

	public ThisYearGameFilterProducerPanel getThisYearGameFilterProducerPanel() {
		return i_thisYearGameFilterProducerPanel;
	}

	public LastFourWeeksGameFilterProducerPanel getLastFourWeeksGameFilterProducerPanel() {
		return i_lastFourWeeksGameFilterProducerPanel;
	}

	public ThisMonthGameFilterProducerPanel getThisMonthGameFilterProducerPanel() {
		return i_thisMonthGameFilterProducerPanel;
	}

	public ThisWeekGameFilterProducerPanel getThisWeekGameFilterProducerPanel() {
		return i_thisWeekGameFilterProducerPanel;
	}

	public TodayGameFilterProducerPanel getTodayGameFilterProducerPanel() {
		return i_todayGameFilterProducerPanel;
	}
}
