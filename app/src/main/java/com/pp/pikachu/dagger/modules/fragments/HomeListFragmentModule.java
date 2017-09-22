package com.pp.pikachu.dagger.modules.fragments;

import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.home.HomeListFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Module
public class HomeListFragmentModule {

  private final HomeListFragment fragment;

  public HomeListFragmentModule(HomeListFragment fragment){
    this.fragment = fragment;
  }

  @Provides @UserScope HomeListFragment provideHomeListFragment(){
    return fragment;
  }
}
