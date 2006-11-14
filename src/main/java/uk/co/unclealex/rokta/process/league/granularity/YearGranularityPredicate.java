/**
 * 
 */
package uk.co.unclealex.rokta.process.league.granularity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author alex
 *
 */
public class YearGranularityPredicate extends GranularityPredicate {

	private static Granularity s_granularity = new WeeklyGranularity();
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#formatDate(java.util.Date)
	 */
	@Override
	public String formatDate(Date datePlayed) {
		return new SimpleDateFormat("'W'ww").format(datePlayed);
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#getCalendarField()
	 */
	@Override
	public Granularity getGranularity() {
		return s_granularity;
	}

	@Override
	public int getMaximumDaysDifference() {
		return 365;
	}

}
