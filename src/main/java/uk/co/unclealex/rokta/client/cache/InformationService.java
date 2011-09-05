package uk.co.unclealex.rokta.client.cache;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

import com.google.common.base.Function;

public interface InformationService {

	<I> void execute(GameFilter gameFilter, Function<CurrentInformation, I> currentInformationFunction,
			InformationCallback<I> informationCallback);

}
