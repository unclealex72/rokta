package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

public class YearGameFilterProducerPanel extends GameFilterProducerComposite {

	private int i_year;

	public YearGameFilterProducerPanel(RoktaAdaptor roktaAdaptor, final Date initialDate, Date earliestDate, Date latestDate, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
		final ListBox yearListBox = new ListBox();
		ChangeHandler changeHandler = new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int year = Integer.valueOf(yearListBox.getValue(yearListBox.getSelectedIndex()));
				setYear(year);
				setGameFilter(createGameFilter());
			}
		};
		yearListBox.addChangeHandler(changeHandler);
		int earliestYear = getYear(earliestDate);
		int latestYear = getYear(latestDate);
		int initialYear = getYear(initialDate);
		setYear(initialYear);
		int selectedIndex = 0;
		for (int year = earliestYear; year <= latestYear; year++) {
			if (initialYear == year) {
				selectedIndex = yearListBox.getItemCount();
			}
			yearListBox.addItem(Integer.toString(year));
		}
		yearListBox.setSelectedIndex(selectedIndex);
		SimplePanel panel = new SimplePanel();
		panel.add(yearListBox);
		initWidget(panel);
	}

	protected int getYear(Date date) {
		return date.getYear() + 1900;
	}
	
	@Override
	protected GameFilter createGameFilter() {
		return new YearGameFilter(new Date(getYear() - 1900, 1, 1));
	}
	
	protected int getYear() {
		return i_year;
	}

	protected void setYear(int year) {
		i_year = year;
	}

}
