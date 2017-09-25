package com.pp.pikachu.ui.activities.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import butterknife.BindView;
import com.pp.pikachu.R;
import com.pp.pikachu.dagger.application.MainApplication;
import com.pp.pikachu.models.Airports;
import com.pp.pikachu.ui.activities.HttpToolBarBaseActivity;
import com.pp.pikachu.ui.activities.home.adapter.HomeAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeActivity extends HttpToolBarBaseActivity {

  @Inject CompositeSubscription compositeSubscription;
  @Inject AlertDialog alertDialog;
  @Inject HomePresenter presenter;
  @Inject HomeAdapter homeAdapter;

  @BindView(R.id.tlHome) TabLayout tlHome;
  @BindView(R.id.vpContainer) ViewPager vpContainer;

  public List<Airports> airportsList;

  @Override protected void setupActivityLayout() {
    setContentView(R.layout.activity_home);
  }

  @Override protected void setupViewElements() {
    airportsList = new ArrayList<>();
    vpContainer.setAdapter(homeAdapter);
    vpContainer.setOffscreenPageLimit(2);
    tlHome.setupWithViewPager(vpContainer);
    presenter.setupTabListener(tlHome, homeAdapter);
  }

  @Override protected boolean isActionBarBackButtonEnabled() {
    return true;
  }

  @Override protected void injectDaggerComponent() {
    MainApplication.get(this).createHomeComponent(this).inject(this);
  }

  @Override public void onNetworkErrorFound(String message) {
    presenter.showAlertDialogError(message);
  }

  @Override public void onHttpErrorUnexpectedFound(String message) {
    presenter.showAlertDialogError(message);
  }

  @Override public void onUnAuthorizedFound(String message) {
    presenter.showAlertDialogError(message);
  }

  @Override public void onApiErrorFound(String message) {
    presenter.showAlertDialogError(message);
  }

  @Override public CompositeSubscription getCompositeSubscription() {
    return compositeSubscription;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    MainApplication.get(this).releaseHomeComponent();
  }

}
