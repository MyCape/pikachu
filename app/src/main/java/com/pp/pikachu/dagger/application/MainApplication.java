package com.pp.pikachu.dagger.application;

import android.content.Context;
import com.pp.pikachu.dagger.component.ApplicationComponent;
import com.pp.pikachu.dagger.component.DaggerApplicationComponent;
import com.pp.pikachu.dagger.component.activities.HomeComponent;
import com.pp.pikachu.dagger.component.activities.MainComponent;
import com.pp.pikachu.dagger.component.fragments.HomeListFragmentComponent;
import com.pp.pikachu.dagger.component.fragments.HomeMapFragmentComponent;
import com.pp.pikachu.dagger.modules.ApplicationModule;
import com.pp.pikachu.dagger.modules.activities.HomeActivityModule;
import com.pp.pikachu.dagger.modules.activities.MainActivityModule;
import com.pp.pikachu.dagger.modules.fragments.HomeListFragmentModule;
import com.pp.pikachu.dagger.modules.fragments.HomeMapFragmentModule;
import com.pp.pikachu.ui.activities.home.HomeActivity;
import com.pp.pikachu.ui.activities.home.HomeListFragment;
import com.pp.pikachu.ui.activities.home.HomeMapFragment;
import com.pp.pikachu.ui.activities.main.MainActivity;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class MainApplication extends BaseApplication {

  private static MainApplication instance;

  private ApplicationComponent applicationComponent;

  private HomeComponent homeComponent;

  private MainComponent mainComponent;

  private HomeListFragmentComponent homeListFragmentComponent;

  private HomeMapFragmentComponent homeMapFragmentComponent;

  public static MainApplication get(Context context) {
    return (MainApplication) context.getApplicationContext();
  }

  public static MainApplication get() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();
    initAppComponent();
    instance = this;
  }

  private void initAppComponent() {
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public HomeComponent createHomeComponent(final HomeActivity homeActivity) {
    homeComponent = applicationComponent.plus(new HomeActivityModule(homeActivity));
    return homeComponent;
  }

  public MainComponent createMainComponent(final MainActivity mainActivity) {
    mainComponent = applicationComponent.plus(new MainActivityModule(mainActivity));
    return mainComponent;
  }

  public HomeListFragmentComponent createHomeListFragmentComponent(final HomeListFragment homeListFragment) {
    homeListFragmentComponent = applicationComponent.plus(new HomeListFragmentModule(homeListFragment));
    return homeListFragmentComponent;
  }

  public HomeMapFragmentComponent createHomeMapFragment(final HomeMapFragment homeMapFragment) {
    homeMapFragmentComponent = applicationComponent.plus(new HomeMapFragmentModule(homeMapFragment));
    return homeMapFragmentComponent;
  }

  public void releaseHomeComponent() {
    homeComponent = null;
  }

  public void releaseMainComponent() {
    mainComponent = null;
  }

  public void releaseHomeListFragmentComponent() {
    homeListFragmentComponent = null;
  }

  public void releaseHomeMapFragment() {
    homeComponent = null;
  }
}
