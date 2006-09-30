/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import uk.co.unclealex.rokta.actions.BasicAction;

/**
 * @author alex
 *
 */
public abstract class LeagueAction extends BasicAction {

	@Override
	protected abstract String executeInternal() throws Exception;

	public boolean isShowLeague() {
		return true;
	}

}
