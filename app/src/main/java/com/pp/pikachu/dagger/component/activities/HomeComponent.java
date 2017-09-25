package com.pp.pikachu.dagger.component.activities;

import com.pp.pikachu.dagger.modules.activities.HomeActivityModule;
import com.pp.pikachu.dagger.modules.api.UserApiModule;
import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.home.HomeActivity;
import dagger.Subcomponent;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Subcomponent(modules = { HomeActivityModule.class, UserApiModule.class }) public interface HomeComponent {
  HomeActivity inject(HomeActivity homeActivity);
}
