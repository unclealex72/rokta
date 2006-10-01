/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import uk.co.unclealex.rokta.process.ProfileManager;
import de.laures.cewolf.DatasetProducer;

/**
 * @author alex
 *
 */
public abstract class ProfileAwareDatasetProducer extends StatisticsAwareDatasetProducer
		implements DatasetProducer {

	private ProfileManager i_profileManager;
	
	/**
	 * @return the profileManager
	 */
	public ProfileManager getProfileManager() {
		return i_profileManager;
	}

	/**
	 * @param profileManager the profileManager to set
	 */
	public void setProfileManager(ProfileManager profileManager) {
		i_profileManager = profileManager;
	}

}
