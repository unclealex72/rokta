package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.GameSummary;

public interface InformationService {

	public Date getDateFirstGamePlayed();

	public Date getDateLastGamePlayed();

	public CurrentInformation getCurrentInformation(GameFilter gameFilter, Day day, int targetStreakSize);

	public GameSummary getLastGameSummary();

}
