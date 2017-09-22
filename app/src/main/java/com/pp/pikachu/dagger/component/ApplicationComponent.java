package com.pp.pikachu.dagger.component;

import com.pp.pikachu.dagger.component.activities.HomeComponent;
import com.pp.pikachu.dagger.component.activities.MainComponent;
import com.pp.pikachu.dagger.component.fragments.HomeListFragmentComponent;
import com.pp.pikachu.dagger.component.fragments.HomeMapFragmentComponent;
import com.pp.pikachu.dagger.modules.ApplicationModule;
import com.pp.pikachu.dagger.modules.activities.HomeActivityModule;
import com.pp.pikachu.dagger.modules.activities.MainActivityModule;
import com.pp.pikachu.dagger.modules.api.ApiModule;
import com.pp.pikachu.dagger.modules.fragments.HomeListFragmentModule;
import com.pp.pikachu.dagger.modules.fragments.HomeMapFragmentModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton @Component(modules = { ApplicationModule.class, ApiModule.class }) public interface ApplicationComponent {

  HomeComponent plus(HomeActivityModule homeActivityModule);

  MainComponent plus(MainActivityModule mainActivityModule);

  HomeListFragmentComponent plus(HomeListFragmentModule homeListFragmentModule);

  HomeMapFragmentComponent plus(HomeMapFragmentModule homeMapFragmentModule);
}
