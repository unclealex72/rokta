/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

import java.util.Date;

/**
 * @author alex
 *
 */
public class BetweenGameRestriction implements GameRestriction {

	private Date i_latestDate;
	private Date i_earliestDate;
	
	public BetweenGameRestriction(Date earliestDate, Date latestDate) {
		super();
		i_earliestDate = earliestDate;
		i_latestDate = latestDate;
	}

	public void accept(GameRestrictionVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the earliestDate
	 */
	public Date getEarliestDate() {
		return i_earliestDate;
	}
	
	/**
	 * @return the latestDate
	 */
	public Date getLatestDate() {
		return i_latestDate;
	}
}
