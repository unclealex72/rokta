package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.shared.model.InitialDates;

public interface DateService {

	public Date normaliseDate(Date date);
	
	public SortedSet<Integer> getValidYears();
	
	public SortedSet<Integer> getValidMonthsForYear(int year);
	
	public SortedSet<Integer> getValidWeeksForYear(int year);

	public InitialDates getInitialDates();
}
