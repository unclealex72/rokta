/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

import java.util.Date;

/**
 * @author alex
 *
 */
public class AfterGameRestriction implements GameRestriction {

	private Date i_earliestDate;
	
	public AfterGameRestriction(Date earliestDate) {
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
