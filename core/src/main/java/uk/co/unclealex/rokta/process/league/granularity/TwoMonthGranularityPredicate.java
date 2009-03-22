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
public class TwoMonthGranularityPredicate extends GranularityPredicate {

	private static Granularity s_granularity = new DailyGranularity();
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#formatDate(java.util.Date)
	 */
	@Override
	public String formatDate(Date datePlayed) {
		return new SimpleDateFormat("dd/MM").format(datePlayed);
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
		return 62;
	}

}
