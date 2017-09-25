package com.pp.pikachu.dagger.modules.activities;

import android.support.v7.app.AlertDialog;
import com.pp.pikachu.api.managers.ApiManager;
import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.main.MainActivity;
import com.pp.pikachu.ui.activities.main.MainPresenter;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Module public class MainActivityModule {

  private final MainActivity activity;

  public MainActivityModule(MainActivity activity) {
    this.activity = activity;
  }

  @Provides @UserScope MainActivity provideMainActivity() {
    return activity;
  }

  @Provides @UserScope CompositeSubscription provideCompositeSubscription() {
    return new CompositeSubscription();
  }

  @Provides @UserScope AlertDialog provideAlertDialog() {
    return new AlertDialog.Builder(activity).create();
  }

  @Provides @UserScope MainPresenter provideMainPresenter(ApiManager apiManager) {
    return new MainPresenter(activity, apiManager);
  }
}
