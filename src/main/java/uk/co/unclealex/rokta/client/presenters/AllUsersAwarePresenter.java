package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.List;

import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class AllUsersAwarePresenter implements Presenter {

	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	
	public AllUsersAwarePresenter(AsyncCallbackExecutor asyncCallbackExecutor) {
		super();
		i_asyncCallbackExecutor = asyncCallbackExecutor;
	}

	@Override
	public final void show(final AcceptsOneWidget container) {
		ExecutableAsyncCallback<String[]> callback = new FailureAsPopupExecutableAsyncCallback<String[]>() {
			@Override
			public void onSuccess(String[] usernames) {
				show(container, Arrays.asList(usernames));
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<String[]> callback) {
				anonymousRoktaService.getAllUsersNames(callback);
			}
		};
		getAsyncCallbackExecutor().execute(callback);
 	}

	protected abstract void show(AcceptsOneWidget container, List<String> usernames);

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

}
