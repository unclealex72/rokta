/**
 * 
 */
package uk.co.unclealex.rokta.process.league.granularity;

import java.util.Calendar;

/**
 * @author alex
 *
 */
public class WeeklyGranularity extends AbstractGranularity {

	public WeeklyGranularity() {
		super(Calendar.WEEK_OF_YEAR, "Weekly");
	}
}
