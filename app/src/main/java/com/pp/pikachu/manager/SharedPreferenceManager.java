package com.pp.pikachu.manager;

import android.content.SharedPreferences;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class SharedPreferenceManager {

  private final SharedPreferences sharedPreferences;

  public SharedPreferenceManager(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public void saveFavoriteAirport(String airportName) {
    sharedPreferences.edit().putString(SharedPreferenceKeys.FAVORIT_AIRPORT, airportName).apply();
  }

  public String getFavoriteAirport() {
    return sharedPreferences.getString(SharedPreferenceKeys.FAVORIT_AIRPORT, "");
  }

  public void clearFavorite() {
    sharedPreferences.edit().clear().apply();
  }
}
