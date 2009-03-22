/**
 * 
 */
package uk.co.unclealex.rokta.process.league.granularity;

import java.util.Date;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang.time.DateUtils;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class GranularityPredicate implements Predicate<Long>, Comparable<GranularityPredicate> {

	public abstract Granularity getGranularity();
	
	public abstract int getMaximumDaysDifference();
	
	public abstract String formatDate(Date datePlayed);
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.granularity.GranularityPredicate#getGameTransformer()
	 */
	public Transformer<Game, String> getGameDescriptor() {
		return new Transformer<Game, String>() {
			public String transform(Game game) {
				return formatDate(game.getDatePlayed());
			}
		};
	}

	public final boolean evaluate(Long millis) {
		return millis <= getMaximumDaysDifference() * DateUtils.MILLIS_PER_DAY;
	}

	public int compareTo(GranularityPredicate o) {
		return new Integer(getMaximumDaysDifference()).compareTo(o.getMaximumDaysDifference());
	}
	
}
