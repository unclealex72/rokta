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
public class MonthGranularityPredicate extends GranularityPredicate {

	private static Granularity s_granularity = new DailyGranularity();
	
	@Override
	public String formatDate(Date datePlayed) {
		String format = new SimpleDateFormat("d").format(datePlayed);
		int dateInMonth = Integer.parseInt(format);
		int lastDigit = dateInMonth % 10;
		String suffix;
		if (lastDigit == 1 && dateInMonth != 11) {
			suffix = "st";
		}
		else if (lastDigit == 2 && dateInMonth != 12) {
			suffix = "nd";
		}
		else if (lastDigit == 3 && dateInMonth != 13) {
			suffix = "rd";
		}
		else {
			suffix = "th";
		}
		return format + suffix;
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
		return 31;
	}

}
