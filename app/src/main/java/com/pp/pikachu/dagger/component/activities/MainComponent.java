package com.pp.pikachu.dagger.component.activities;

import com.pp.pikachu.dagger.modules.activities.MainActivityModule;
import com.pp.pikachu.dagger.modules.api.UserApiModule;
import com.pp.pikachu.dagger.scope.UserScope;
import com.pp.pikachu.ui.activities.main.MainActivity;
import dagger.Subcomponent;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Subcomponent(modules = { MainActivityModule.class, UserApiModule.class }) public interface MainComponent {
  MainActivity inject(MainActivity mainActivity);
}
