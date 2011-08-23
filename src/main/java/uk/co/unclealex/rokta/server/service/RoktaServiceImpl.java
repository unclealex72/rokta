package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import com.google.common.collect.Iterables;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.HandDistribution;
import uk.co.unclealex.rokta.shared.model.InitialDates;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaService;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

public class RoktaServiceImpl implements AnonymousRoktaService, UserRoktaService {

	private CacheService i_cacheService;
	private InformationService i_informationService;
	private NewGameService i_newGameService;
	private PersonService i_personService;
	
	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		return getNewGameService().getInitialPlayers(date);
	}

	@Override
	public void submitGame(Game game) {
		getNewGameService().submitGame(game);
	}

	@Override
	public Date getDateFirstGamePlayed() {
		return getInformationService().getDateFirstGamePlayed();
	}

	@Override
	public Date getDateLastGamePlayed() {
		return getInformationService().getDateLastGamePlayed();
	}
	
	@Override
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, int currentYear, int currentMonth,
			int currentDay, int targetStreaksSize) {
		return getInformationService().getCurrentInformation(gameFilter, currentYear, currentMonth, currentDay, targetStreaksSize);
	}

	@Override
	public HandDistribution createHandDistributionGraph(GameFilter gameFilter, String personName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandDistribution createOpeningHandDistributionGraph(GameFilter gameFilter, String personName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAllUsersNames() {
		return Iterables.toArray(getPersonService().getAllUsernames(), String.class);
	}

	@Override
	public String[] getAllPlayerNames() {
		return Iterables.toArray(getPersonService().getAllPlayerNames(), String.class);
	}

	@Override
	public InitialDates getInitialDates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public CacheService getCacheService() {
		return i_cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		i_cacheService = cacheService;
	}

	public InformationService getInformationService() {
		return i_informationService;
	}

	public void setInformationService(InformationService informationService) {
		i_informationService = informationService;
	}

	public NewGameService getNewGameService() {
		return i_newGameService;
	}

	public void setNewGameService(NewGameService newGameService) {
		i_newGameService = newGameService;
	}

	public PersonService getPersonService() {
		return i_personService;
	}

	public void setPersonService(PersonService personService) {
		i_personService = personService;
	};

}
