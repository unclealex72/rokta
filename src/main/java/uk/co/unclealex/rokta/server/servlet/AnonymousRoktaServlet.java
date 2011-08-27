package uk.co.unclealex.rokta.server.servlet;

import java.util.Date;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaService;

public class AnonymousRoktaServlet extends AbstractRoktaServlet<AnonymousRoktaService> implements AnonymousRoktaService {

	@Override
	protected Class<AnonymousRoktaService> getRoktaServiceClass() {
		return AnonymousRoktaService.class;
	}
	
	@Override
	public Date getDateFirstGamePlayed() {
		return createRoktaService().getDateFirstGamePlayed();
	}

	@Override
	public Date getDateLastGamePlayed() {
		return createRoktaService().getDateLastGamePlayed();
	}

	@Override
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, Date currentDate, int targetStreaksSize) {
		return createRoktaService().getCurrentInformation(gameFilter, currentDate, targetStreaksSize);
	}
	
	@Override
	public String[] getAllUsersNames() {
		return createRoktaService().getAllUsersNames();
	}

	@Override
	public String[] getAllPlayerNames() {
		return createRoktaService().getAllPlayerNames();
	}

	@Override
	public boolean authenticate(String username, String password) {
		return createRoktaService().authenticate(username, password);
	}

}
