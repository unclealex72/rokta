/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

import java.util.Date;

/**
 * @author alex
 *
 */
public class BeforeGameRestriction implements GameRestriction {

	private Date i_latestDate;
	
	public BeforeGameRestriction(Date latestDate) {
		super();
		i_latestDate = latestDate;
	}

	public void accept(GameRestrictionVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the latestDate
	 */
	public Date getLatestDate() {
		return i_latestDate;
	}
}
