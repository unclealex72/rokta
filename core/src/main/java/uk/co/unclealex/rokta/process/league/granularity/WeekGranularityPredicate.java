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
public class WeekGranularityPredicate extends GranularityPredicate {

	private static Granularity s_granularity = new DailyGranularity();
	
	@Override
	public Granularity getGranularity() {
		return s_granularity;
	}

	@Override
	public int getMaximumDaysDifference() {
		return 7;
	}

	@Override
	public String formatDate(Date datePlayed) {
		return new SimpleDateFormat("EEEEE").format(datePlayed);
	}

}
