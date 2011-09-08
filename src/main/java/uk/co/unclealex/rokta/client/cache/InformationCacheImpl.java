package uk.co.unclealex.rokta.client.cache;

import java.util.Date;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InformationCacheImpl implements InformationCache {

	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private final EventBus i_eventBus;

	private CurrentInformation i_currentInformation;
	private Date i_lastRequestTime;
	private GameFilter i_lastGameFilter;
	private Integer i_lastTargetStreaksSize;
	
	@Inject
	public InformationCacheImpl(AsyncCallbackExecutor asyncCallbackExecutor, EventBus eventBus) {
		super();
		i_asyncCallbackExecutor = asyncCallbackExecutor;
		i_eventBus = eventBus;
	}

	@Override
	public void clearCache() {
		setCurrentInformation(null);
	}
	
	@Override
	public void useCurrentInformation(
			final AsyncCallback<CurrentInformation> callback, final GameFilter gameFilter, final int targetStreaksSize) {
		if (getCurrentInformation() == null ||
				!gameFilter.equals(getLastGameFilter()) || 
				!Integer.valueOf(targetStreaksSize).equals(getLastTargetStreaksSize())) {
			updateRequired(callback, gameFilter, targetStreaksSize);
		}
		else {
			ExecutableAsyncCallback<Date> lastDateCallback = new FailureAsPopupExecutableAsyncCallback<Date>() {
				@Override
				public void onSuccess(Date lastDatePlayed) {
					if (lastDatePlayed.after(getLastRequestTime())) {
						updateRequired(callback, gameFilter, targetStreaksSize);
					}
					else {
						callback.onSuccess(getCurrentInformation());
					}
				}
				@Override
				public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
						AsyncCallback<Date> callback) {
					anonymousRoktaService.getDateLastGamePlayed(callback);
				}
			};
			getAsyncCallbackExecutor().executeAndWait(lastDateCallback, "Finding when the last game was played");
		}
	}

	protected void updateRequired(final AsyncCallback<CurrentInformation> callback, final GameFilter gameFilter, final int targetStreaksSize) {
		final Date now = new Date();
		setLastRequestTime(now);
		setLastGameFilter(gameFilter);
		setLastTargetStreaksSize(targetStreaksSize);
		ExecutableAsyncCallback<CurrentInformation> currentInformationCallback = new FailureAsPopupExecutableAsyncCallback<CurrentInformation>() {
			@Override
			public void onSuccess(CurrentInformation currentInformation) {
				setCurrentInformation(currentInformation);
				getEventBus().fireEvent(new CurrentInformationChangeEvent(currentInformation));
				callback.onSuccess(currentInformation);
			}
			@Override
			public void onFailure(Throwable cause) {
				callback.onFailure(cause);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<CurrentInformation> callback) {
				anonymousRoktaService.getCurrentInformation(gameFilter, now, targetStreaksSize, callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(currentInformationCallback, "Loading all current league information");
	}

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

	public CurrentInformation getCurrentInformation() {
		return i_currentInformation;
	}

	public void setCurrentInformation(CurrentInformation currentInformation) {
		i_currentInformation = currentInformation;
	}

	public Date getLastRequestTime() {
		return i_lastRequestTime;
	}

	public void setLastRequestTime(Date lastRequestTime) {
		i_lastRequestTime = lastRequestTime;
	}

	public GameFilter getLastGameFilter() {
		return i_lastGameFilter;
	}

	public void setLastGameFilter(GameFilter lastGameFilter) {
		i_lastGameFilter = lastGameFilter;
	}

	public Integer getLastTargetStreaksSize() {
		return i_lastTargetStreaksSize;
	}

	public void setLastTargetStreaksSize(Integer lastTargetStreaksSize) {
		i_lastTargetStreaksSize = lastTargetStreaksSize;
	}

	public EventBus getEventBus() {
		return i_eventBus;
	}

}
