/**
 * 
 */
package uk.co.unclealex.rokta.process.league.granularity;

import java.util.Calendar;

/**
 * @author alex
 *
 */
public class DailyGranularity extends AbstractGranularity {

	public DailyGranularity() {
		super(Calendar.DAY_OF_YEAR, "Daily");
	}
}
