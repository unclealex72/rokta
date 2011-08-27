package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaService;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

import com.google.common.collect.Iterables;

public class RoktaServiceImpl implements AnonymousRoktaService, UserRoktaService {

	private CacheService i_cacheService;
	private InformationService i_informationService;
	private NewGameService i_newGameService;
	private PersonService i_personService;
	
	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		return getNewGameService().getInitialPlayers(new Day(date));
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
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, Date currentDate, int targetStreaksSize) {
		return getInformationService().getCurrentInformation(gameFilter, new Day(currentDate), targetStreaksSize);
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
	public void clearCache() {
		getCacheService().clearCache();
	}
	
	@Override
	public void removeLastGame() {
		getNewGameService().removeLastGame();
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
