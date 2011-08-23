package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

public interface InformationService {

	public Date getDateFirstGamePlayed();

	public Date getDateLastGamePlayed();

	public CurrentInformation getCurrentInformation(GameFilter gameFilter, int currentYear, int currentMonth,
			int currentDay, int targetStreakSize);

}
