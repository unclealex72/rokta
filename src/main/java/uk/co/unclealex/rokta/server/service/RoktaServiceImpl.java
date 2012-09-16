package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import uk.co.unclealex.googleauth.OauthPrincipal;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;

public class RoktaServiceImpl implements RoktaService {

	private CacheService i_cacheService;
	private InformationService i_informationService;
	private NewGameService i_newGameService;
	private PersonService i_personService;
	private Supplier<HttpServletRequest> httpServletRequestSupplier;
	private PersonDao i_personDao;
	
	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		return getNewGameService().getInitialPlayers(new Day(date));
	}

	@Override
	public void forceSignIn() {
		// Don't need to do anything.
	}
	
	@Override
	public GameSummary getLastGameSummary() {
		return getInformationService().getLastGameSummary();
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
	public String getUserPrincipal() {
	  OauthPrincipal principal = (OauthPrincipal) getHttpServletRequestSupplier().get().getUserPrincipal();
	  return getPersonDao().getPersonByEmailAddress(principal.getUserinfo().getEmail()).getName();
	}

	@Override
	public void logout() {
		getHttpServletRequestSupplier().get().getSession(true).invalidate();
	}

	@Override
	public LoggedInUser getCurrentlyLoggedInUser() {
		String username = getUserPrincipal();
		return new LoggedInUser(username, getColourForUser(username));
	}
	
	@Override
	public Colour getColourForUser(String username) {
		return getPersonDao().getPersonByName(username).getGraphingColour();
	}
	
	@Override
	public void updateColour(String username, Colour colour) {
		getPersonService().changeGraphingColour(username, colour);
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
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

  public Supplier<HttpServletRequest> getHttpServletRequestSupplier() {
    return httpServletRequestSupplier;
  }

  public void setHttpServletRequestSupplier(Supplier<HttpServletRequest> httpServletRequestSupplier) {
    this.httpServletRequestSupplier = httpServletRequestSupplier;
  };

}
