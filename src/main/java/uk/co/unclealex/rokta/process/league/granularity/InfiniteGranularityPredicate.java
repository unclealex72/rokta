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
public class InfiniteGranularityPredicate extends GranularityPredicate {

	private static Granularity s_granularity = new WeeklyGranularity();
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#formatDate(java.util.Date)
	 */
	@Override
	public String formatDate(Date datePlayed) {
		return new SimpleDateFormat("'W'ww yyyy").format(datePlayed);
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#getCalendarField()
	 */
	@Override
	public Granularity getGranularity() {
		return s_granularity;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#getMaximumDaysDifference()
	 */
	@Override
	public int getMaximumDaysDifference() {
		return Integer.MAX_VALUE;
	}

}
