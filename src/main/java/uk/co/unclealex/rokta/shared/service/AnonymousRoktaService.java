package uk.co.unclealex.rokta.shared.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.HandDistribution;
import uk.co.unclealex.rokta.shared.model.InitialDates;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.StreaksLeague;

@RemoteServiceRelativePath("anonymous")
public interface AnonymousRoktaService extends RemoteService {

	public League[] getLeague(GameFilter gameFilter, Date now);

	public StreaksLeague getWinningStreaks(GameFilter gameFilter, int targetSize);
	public StreaksLeague getLosingStreaks(GameFilter gameFilter, int targetSize);
	public StreaksLeague getCurrentWinningStreaks(GameFilter gameFilter);
	public StreaksLeague getCurrentLosingStreaks(GameFilter gameFilter);
	public StreaksLeague getLosingStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);
	public StreaksLeague getWinningStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);

	public HandDistribution createHandDistributionGraph(GameFilter gameFilter, String personName);
	public HandDistribution createOpeningHandDistributionGraph(GameFilter gameFilter, String personName);	

	public String[] getAllUsersNames();
	public String[] getAllPlayerNames();
	
	public InitialDates getInitialDates();
	
	public boolean authenticate(String username, String password);
}
