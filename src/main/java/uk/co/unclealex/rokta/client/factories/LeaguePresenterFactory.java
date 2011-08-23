package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.LeaguePresenter;

public interface LeaguePresenterFactory {

	LeaguePresenter createLeaguePresenter(GameFilter gameFilter);

}
