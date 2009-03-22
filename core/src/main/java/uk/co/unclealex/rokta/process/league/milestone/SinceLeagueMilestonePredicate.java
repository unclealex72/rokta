/**
 * 
 */
package uk.co.unclealex.rokta.process.league.milestone;

import java.util.Date;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class SinceLeagueMilestonePredicate extends
		AbstractLeagueMilestonePredicate {

	private Date i_since;
	
	/**
	 * @param since
	 */
	public SinceLeagueMilestonePredicate(Date since) {
		super();
		i_since = since;
	}

	public boolean evaluate(Game game) {
		return !game.getDatePlayed().before(getSince());
	}

	/**
	 * @return the since
	 */
	public Date getSince() {
		return i_since;
	}

}
