package com.pp.pikachu.dagger.modules.fragments;

import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.home.HomeMapFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Module public class HomeMapFragmentModule {

  private final HomeMapFragment fragment;

  public HomeMapFragmentModule(HomeMapFragment fragment) {
    this.fragment = fragment;
  }

  @Provides @UserScope HomeMapFragment provideHomeMapFragment() {
    return fragment;
  }
}
