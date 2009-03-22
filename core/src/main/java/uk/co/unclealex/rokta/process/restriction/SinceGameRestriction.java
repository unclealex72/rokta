/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

import java.util.Date;

/**
 * @author alex
 *
 */
public class SinceGameRestriction implements GameRestriction {

	private Date i_earliestDate;
	
	public SinceGameRestriction(Date earliestDate) {
		super();
		i_earliestDate = earliestDate;
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
}
