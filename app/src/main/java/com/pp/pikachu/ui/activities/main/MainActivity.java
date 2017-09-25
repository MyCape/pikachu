package com.pp.pikachu.ui.activities.main;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.pp.pikachu.R;
import com.pp.pikachu.dagger.application.MainApplication;
import com.pp.pikachu.dagger.modules.DataParser;
import com.pp.pikachu.manager.AppActivityManager;
import com.pp.pikachu.ui.activities.HttpToolBarBaseActivity;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

/**
 * Most of the methods are implemented in abstract in the superclass so we can override them
 * and implement the proper process, and so the sub classes can have a uniform structure.
 */
public class MainActivity extends HttpToolBarBaseActivity {

  @Inject AlertDialog alertDialog;
  @Inject MainPresenter presenter;
  @Inject CompositeSubscription compositeSubscription;
  @Inject DataParser dataParser;
  @Inject AppActivityManager appActivityManager;

  @Override protected void setupActivityLayout() {
    setContentView(R.layout.activity_main);
  }

  @Override protected void setupViewElements() {
    processAirports();
  }

  @Override protected boolean isActionBarBackButtonEnabled() {
    return false;
  }

  @Override protected void injectDaggerComponent() {
    MainApplication.get(this).createMainComponent(this).inject(this);
  }

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      getWindow().getDecorView()
          .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }

  @Override public void onNetworkErrorFound(String message) {
    presenter.showAlertDialogError(message);
    presenter.handleError();
  }

  @Override public void onHttpErrorUnexpectedFound(String message) {
    presenter.showAlertDialogError(message);
    presenter.handleError();
  }

  @Override public void onUnAuthorizedFound(String message) {
    presenter.showAlertDialogError(message);
    presenter.handleError();
  }

  @Override public void onApiErrorFound(String message) {
    presenter.showAlertDialogError(message);
    presenter.handleError();
  }

  @Override public CompositeSubscription getCompositeSubscription() {
    return compositeSubscription;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    MainApplication.get(this).releaseMainComponent();
  }

  /**
   * Once everything is setup, we will call the api for the data if the database is empty.
   * If the database is not empty, then we will direct the user to the home page.
   *
   */
  private void processAirports() {
    if (!presenter.isDataExisting()) {
      compositeSubscription.add(presenter.getAirports());
    } else {
      new Handler().postDelayed(() -> {
        presenter.goToHomePage();
      }, 1000);
    }
  }
}
