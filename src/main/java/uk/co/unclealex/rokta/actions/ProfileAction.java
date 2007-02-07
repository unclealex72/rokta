/**
 * 
 */
package uk.co.unclealex.rokta.actions;

import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.WinLoseCounter;
import uk.co.unclealex.rokta.process.ProfileManager;
import uk.co.unclealex.rokta.process.dataset.HandCountingDatasetProducer;

/**
 * @author alex
 *
 */
public class ProfileAction extends RoktaAction {

	private HandCountingDatasetProducer i_handChoiceDatasetProducer;
	private HandCountingDatasetProducer i_openingGambitDatasetProducer;
	private SortedMap<Person, WinLoseCounter> i_headToHeadRoundWinRate;

	private ProfileManager i_profileManager;
	
	private Person i_person;
	
	@Override
	protected String executeInternal() {
		Person person = getPerson();
		ProfileManager manager = getProfileManager();
		manager.setPerson(person);
    manager.setGames(getGames());
		getHandChoiceDatasetProducer().setHandCount(manager.countHands());
		getOpeningGambitDatasetProducer().setHandCount(manager.countOpeningGambits());
		setHeadToHeadRoundWinRate(manager.getHeadToHeadRoundWinRate());
		return SUCCESS;
	}

	public boolean isShowProfile() {
		return true;
	}

	/**
	 * @return the headToHeadRoundWinRate
	 */
	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate() {
		return i_headToHeadRoundWinRate;
	}


	/**
	 * @param headToHeadRoundWinRate the headToHeadRoundWinRate to set
	 */
	public void setHeadToHeadRoundWinRate(
			SortedMap<Person, WinLoseCounter> headToHeadRoundWinRate) {
		i_headToHeadRoundWinRate = headToHeadRoundWinRate;
	}

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

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return i_person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		i_person = person;
	}

	/**
	 * @return the handChoiceDatasetProvider
	 */
	public HandCountingDatasetProducer getHandChoiceDatasetProducer() {
		return i_handChoiceDatasetProducer;
	}

	/**
	 * @param handChoiceDatasetProvider the handChoiceDatasetProvider to set
	 */
	public void setHandChoiceDatasetProducer(
			HandCountingDatasetProducer handChoiceDatasetProvider) {
		i_handChoiceDatasetProducer = handChoiceDatasetProvider;
	}

	/**
	 * @return the openingGambitDatasetProvider
	 */
	public HandCountingDatasetProducer getOpeningGambitDatasetProducer() {
		return i_openingGambitDatasetProducer;
	}

	/**
	 * @param openingGambitDatasetProvider the openingGambitDatasetProvider to set
	 */
	public void setOpeningGambitDatasetProducer(
			HandCountingDatasetProducer openingGambitDatasetProvider) {
		i_openingGambitDatasetProducer = openingGambitDatasetProvider;
	}
}
