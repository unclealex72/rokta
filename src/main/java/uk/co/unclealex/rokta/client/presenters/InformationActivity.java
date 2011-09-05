package uk.co.unclealex.rokta.client.presenters;

import uk.co.unclealex.rokta.client.cache.InformationCallback;
import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

import com.google.common.base.Function;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class InformationActivity<D extends IsWidget, I> extends AbstractGameFilterActivity<D> {

	private final InformationService i_informationService;

	public InformationActivity(GameFilter gameFilter, InformationService informationService) {
		super(gameFilter);
		i_informationService = informationService;
	}

	@Override
	public void start(final GameFilter gameFilter, final AcceptsOneWidget panel, EventBus eventBus) {
		Function<CurrentInformation, I> currentInformationFunction = new Function<CurrentInformation, I>() {
			@Override
			public I apply(CurrentInformation currentInformation) {
				return asSpecificInformation(currentInformation);
			}
		};
		InformationCallback<I> informationCallback = new InformationCallback<I>() {
			public void execute(I information) {
				show(gameFilter, panel, information);
			}
		};
		getInformationService().execute(gameFilter, currentInformationFunction, informationCallback);
	}

	protected abstract I asSpecificInformation(CurrentInformation currentInformation);
	
	protected abstract void show(GameFilter gameFilter, AcceptsOneWidget panel, I currentInformation);
	
	public InformationService getInformationService() {
		return i_informationService;
	}

	
}
