package com.pp.pikachu.dagger.component.fragments;

import com.pp.pikachu.dagger.modules.fragments.HomeListFragmentModule;
import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.home.HomeListFragment;
import dagger.Subcomponent;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Subcomponent(modules = { HomeListFragmentModule.class }) public interface HomeListFragmentComponent {
  HomeListFragment inject(HomeListFragment homeListFragment);
}
