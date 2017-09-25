package com.pp.pikachu.dagger.application;

import android.app.Application;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class BaseApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Realm.init(this);
    // The RealmConfiguration is created using the builder pattern.
    RealmConfiguration config = new RealmConfiguration.Builder().build();
    // Use the config
    Realm.setDefaultConfiguration(config);
  }
}
