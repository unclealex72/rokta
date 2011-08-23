package uk.co.unclealex.rokta.shared.service;

import java.util.Date;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.HandDistribution;
import uk.co.unclealex.rokta.shared.model.InitialDates;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("anonymous")
public interface AnonymousRoktaService extends RemoteService {

	public Date getDateFirstGamePlayed();
	public Date getDateLastGamePlayed();
	
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, int currentYear, int currentMonth, int currentDay, int targetStreaksSize);

	public HandDistribution createHandDistributionGraph(GameFilter gameFilter, String personName);
	public HandDistribution createOpeningHandDistributionGraph(GameFilter gameFilter, String personName);	

	public String[] getAllUsersNames();
	public String[] getAllPlayerNames();
	
	public InitialDates getInitialDates();
	
	public boolean authenticate(String username, String password);
}
