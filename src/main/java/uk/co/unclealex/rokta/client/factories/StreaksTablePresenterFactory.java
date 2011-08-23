package uk.co.unclealex.rokta.client.factories;

import java.util.SortedSet;

import uk.co.unclealex.rokta.client.presenters.StreaksTablePresenter;
import uk.co.unclealex.rokta.shared.model.Streak;

public interface StreaksTablePresenterFactory {

	public StreaksTablePresenter createStreaksTablePresenter(SortedSet<Streak> streaks);
}
