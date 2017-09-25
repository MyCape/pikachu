package com.pp.pikachu.ui.activities;

import com.pp.pikachu.R;
import com.pp.pikachu.api.utils.RetrofitException;
import retrofit2.Response;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public abstract class HttpToolBarBaseActivity extends ToolBarBaseActivity {

  private static final int UNAUTHORIZED = 401;

  public abstract void onNetworkErrorFound(final String message);

  public abstract void onHttpErrorUnexpectedFound(final String message);

  public abstract void onUnAuthorizedFound(final String message);

  public abstract void onApiErrorFound(final String message);

  public abstract CompositeSubscription getCompositeSubscription();

  public void handleHttpError(final RetrofitException e) {
    if (e.getKind().equals(RetrofitException.Kind.NETWORK)) {
      onNetworkErrorFound(getString(R.string.network_issue_found));
    } else if (e.getKind().equals(RetrofitException.Kind.HTTP)) {
      final Response response = e.getResponse();
      if (response != null) {
        if (response.code() == UNAUTHORIZED) {
          onUnAuthorizedFound(getString(R.string.unauthorized));
        } else {
          onApiErrorFound(getString(R.string.data_issue_found));
        }
      }
    } else if (e.getKind().equals(RetrofitException.Kind.UNEXPECTED)) {
      onHttpErrorUnexpectedFound(getString(R.string.http_error_unexpected));
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (getCompositeSubscription().hasSubscriptions()) {
      getCompositeSubscription().unsubscribe();
    }
  }
}
