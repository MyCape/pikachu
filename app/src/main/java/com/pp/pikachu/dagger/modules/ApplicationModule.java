package com.pp.pikachu.dagger.modules;

import android.app.Application;
import android.content.Context;
import com.pp.pikachu.manager.AppActivityManager;
import com.pp.pikachu.manager.SharedPreferenceKeys;
import com.pp.pikachu.manager.SharedPreferenceManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton @Module public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton Application provideApplication() {
    return application;
  }

  @Provides @Singleton DataParser provideDataParser() {
    return new DataParser();
  }

  @Provides @Singleton AppActivityManager provideAppActivityManager() {
    return new AppActivityManager();
  }

  @Provides @Singleton SharedPreferenceManager provideSharedPreferenceManager() {
    return new SharedPreferenceManager(application.getApplicationContext()
        .getApplicationContext()
        .getSharedPreferences(SharedPreferenceKeys.PIKACHU_PREFERENCE, Context.MODE_PRIVATE));
  }
}
