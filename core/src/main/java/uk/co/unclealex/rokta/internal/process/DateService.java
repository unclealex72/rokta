package uk.co.unclealex.rokta.internal.process;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.pub.views.InitialDatesView;

public interface DateService {

	public Date normaliseDate(Date date);
	
	public SortedSet<Integer> getValidYears();
	
	public SortedSet<Integer> getValidMonthsForYear(int year);
	
	public SortedSet<Integer> getValidWeeksForYear(int year);

	public InitialDatesView getInitialDates();
}