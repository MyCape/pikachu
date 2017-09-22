package com.pp.pikachu.dagger.modules.activities;

import android.support.v7.app.AlertDialog;
import com.pp.pikachu.api.managers.ApiManager;
import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.home.HomeActivity;
import com.pp.pikachu.ui.activities.home.HomePresenter;
import com.pp.pikachu.ui.activities.home.adapter.HomeAdapter;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Module public class HomeActivityModule {

  private final HomeActivity activity;

  public HomeActivityModule(HomeActivity activity) {
    this.activity = activity;
  }

  @Provides @UserScope HomeActivity provideHomeActivity() {
    return activity;
  }

  @Provides @UserScope HomePresenter provideHomePresenter(ApiManager apiManager) {
    return new HomePresenter(activity, apiManager);
  }

  @Provides @UserScope CompositeSubscription provideCompositeSubscription() {
    return new CompositeSubscription();
  }

  @Provides @UserScope AlertDialog provideAlertDialog() {
    return new AlertDialog.Builder(activity).create();
  }

  @Provides @UserScope HomeAdapter provideHomeAdapter() {
    return new HomeAdapter(activity.getSupportFragmentManager(), activity);
  }
}
