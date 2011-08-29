package uk.co.unclealex.rokta.server.service;

import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.ColourDao;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.shared.model.ColourView;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;
import uk.co.unclealex.rokta.shared.service.SecurityInvalidator;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class RoktaServiceImpl implements RoktaService {

	private CacheService i_cacheService;
	private InformationService i_informationService;
	private NewGameService i_newGameService;
	private PersonService i_personService;
	private SecurityInvalidator i_securityInvalidator;
	private AuthenticationManager i_authenticationManager;
	private ColourDao i_colourDao;
	
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
	public boolean authenticate(String username, String password) {
		try {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
			getAuthenticationManager().authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return true;
		}
		catch (AuthenticationException e) {
			logout();
			return false;
		}
	}

	protected boolean isAuthenticatedAnonymously(Authentication authentication) {
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.size() == 1 && "ROLE_ANONYMOUS".equals(authorities.iterator().next().getAuthority());
	}

	@Override
	public String getUserPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication==null || isAuthenticatedAnonymously(authentication)?
				null:((UserDetails) authentication.getPrincipal()).getUsername();
	}

	@Override
	public void logout() {
		getSecurityInvalidator().invalidate();
		SecurityContextHolder.clearContext();
	}

	@Override
	public LoggedInUser getCurrentlyLoggedInUser() {
		String username = getUserPrincipal();
		return new LoggedInUser(username, getColourViewForUser(username));
	}
	
	@Override
	public ColourView getColourViewForUser(String username) {
		Colour colour = getColourDao().getColourForUser(username);
		return colourToColourView(colour);
	}
	
	@Override
	public void updateColour(String username, ColourView colourView) {
		getPersonService().changeGraphingColour(username, colourView.getName());
	}
	
	@Override
	public void updatePassword(String username, String newPassword) {
		getPersonService().changePassword(username, newPassword);
	}
	
	@Override
	public ColourView[] getAllColourViews() {
		Function<Colour, ColourView> function = new Function<Colour, ColourView>() {
			public ColourView apply(Colour colour) {
				return colourToColourView(colour);
			}
		};
		SortedSet<Colour> allColours = getColourDao().getAll();
		return Iterables.toArray(Iterables.transform(allColours, function), ColourView.class);
	}
	
	protected ColourView colourToColourView(Colour colour) {
		short red = colour.getRed();
		short green = colour.getGreen();
		short blue = colour.getBlue();
    int brightness =
    		(int) Math.sqrt(.241 * (double) red * (double) red + .691 * (double) green * (double) green + .068 * (double) blue * (double) blue);
		return new ColourView(colour.getName(), colour.getHtmlName(), red, green, blue, brightness < 130);
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

	public SecurityInvalidator getSecurityInvalidator() {
		return i_securityInvalidator;
	}

	public void setSecurityInvalidator(SecurityInvalidator securityInvalidator) {
		i_securityInvalidator = securityInvalidator;
	}

	public AuthenticationManager getAuthenticationManager() {
		return i_authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		i_authenticationManager = authenticationManager;
	}

	public ColourDao getColourDao() {
		return i_colourDao;
	}

	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	};

}
