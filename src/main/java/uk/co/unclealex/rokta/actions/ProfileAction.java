/**
 * 
 */
package uk.co.unclealex.rokta.actions;

import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.WinLoseCounter;
import uk.co.unclealex.rokta.process.ProfileManager;
import uk.co.unclealex.rokta.process.dataset.HandChoiceDatasetProducer;
import uk.co.unclealex.rokta.process.dataset.OpeningGambitDatasetProducer;

/**
 * @author alex
 *
 */
public class ProfileAction extends BasicAction {

	private HandChoiceDatasetProducer i_handChoiceDatasetProducer;
	private OpeningGambitDatasetProducer i_openingGambitDatasetProducer;
	private SortedMap<Person, WinLoseCounter> i_headToHeadRoundWinRate;

	private ProfileManager i_profileManager;
	private String i_name;
	private Person i_person;
	
	@Override
	protected String executeInternal() throws Exception {
		Person person = getPersonDao().getPersonByName(getName());
		setPerson(person);
		ProfileManager manager = getProfileManager();
		manager.setPerson(person);
    manager.setGames(getGameDao().getAllGames());
		getHandChoiceDatasetProducer().setProfileManager(manager);
		getOpeningGambitDatasetProducer().setProfileManager(manager);
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
	 * @return the name
	 */
	public String getName() {
		return i_name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		i_name = name;
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
	public HandChoiceDatasetProducer getHandChoiceDatasetProducer() {
		return i_handChoiceDatasetProducer;
	}

	/**
	 * @param handChoiceDatasetProvider the handChoiceDatasetProvider to set
	 */
	public void setHandChoiceDatasetProducer(
			HandChoiceDatasetProducer handChoiceDatasetProvider) {
		i_handChoiceDatasetProducer = handChoiceDatasetProvider;
	}

	/**
	 * @return the openingGambitDatasetProvider
	 */
	public OpeningGambitDatasetProducer getOpeningGambitDatasetProducer() {
		return i_openingGambitDatasetProducer;
	}

	/**
	 * @param openingGambitDatasetProvider the openingGambitDatasetProvider to set
	 */
	public void setOpeningGambitDatasetProducer(
			OpeningGambitDatasetProducer openingGambitDatasetProvider) {
		i_openingGambitDatasetProducer = openingGambitDatasetProvider;
	}
}
