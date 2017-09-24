package com.pp.pikachu.ui.activities.main;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pp.pikachu.api.managers.ApiManager;
import com.pp.pikachu.api.utils.AppConstants;
import com.pp.pikachu.api.utils.RetrofitException;
import com.pp.pikachu.api.utils.SimpleObserver;
import com.pp.pikachu.models.Airports;
import com.pp.pikachu.ui.activities.BasePresenter;
import io.realm.Realm;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Subscription;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class MainPresenter extends BasePresenter {

  private final MainActivity activity;
  private final ApiManager apiManager;

  public MainPresenter(MainActivity activity, ApiManager apiManager) {
    this.activity = activity;
    this.apiManager = apiManager;
  }

  @Override public Activity getActivity() {
    return activity;
  }

  @Override public AlertDialog getAlertDialog() {
    return activity.alertDialog;
  }

  Subscription getAirports() {
    return apiManager.getAirport().subscribe(new SimpleObserver<Object>() {
      @Override public void onNext(Object object) {
        handleProcessing(object);
      }

      @Override public void onError(Throwable e) {
        activity.handleHttpError((RetrofitException) e);
      }
    });
  }

  /**
   * Handling of object that is being returned by the api,
   * object is being parsed into a jsonArray then passed on other method for saving.
   * @param object
   */
  private void handleProcessing(Object object) {
    JsonObject jsonObject = new Gson().toJsonTree(object).getAsJsonObject();
    JSONArray jsonArray = new JSONArray();
    List<String> keysList = activity.dataParser.parseKeys(jsonObject);
    for (int y = 0; y < jsonObject.size(); y++) {
      jsonArray.put(jsonObject.get(keysList.get(y)));
    }
    processSaving(jsonArray);
  }

  /**
   * JSONArray data is processed for database saving using realm
   * @param formattedJSON
   */
  private void processSaving(JSONArray formattedJSON) {
    Realm realm = Realm.getDefaultInstance();
    try {

      realm.beginTransaction();
      for (int i = 0; i < formattedJSON.length(); i++) {
        String newString = formattedJSON.get(i).toString();
        JSONObject jsonObject =
            new JSONObject(newString.substring(newString.indexOf("{"), newString.lastIndexOf("}") + 1));
        Airports airports = realm.createObject(Airports.class, jsonObject.getString(AppConstants.ICAO));
        airports.setIata(jsonObject.optString(AppConstants.IATA, ""));
        airports.setName(jsonObject.optString(AppConstants.NAME, ""));
        airports.setCity(jsonObject.optString(AppConstants.CITY, ""));
        airports.setCountry(jsonObject.optString(AppConstants.COUNTRY, ""));
        airports.setElevation(jsonObject.optString(AppConstants.ELEVATION, ""));
        airports.setLatitude(jsonObject.optString(AppConstants.LATITUDE, ""));
        airports.setLongitude(jsonObject.optString(AppConstants.LONGITUDE, ""));
        airports.setTz(jsonObject.optString(AppConstants.TZ, ""));
      }
      realm.commitTransaction();
      goToHomePage();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * Once all the data has been saved, the user will be directed to the home page.
   */
  public void goToHomePage() {
    activity.appActivityManager.launchHomePage(activity);
  }

  /**
   * Database is being checked if it is empty so we can call the api in case the database
   * is empty.
   * @return
   */
  public boolean isDataExisting() {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    if (realm.isEmpty()) {
      realm.commitTransaction();
      return false;
    } else {
      realm.commitTransaction();
      return true;
    }
  }

  /**
   * Close the app if there's any error within the api, network, etc.
   */
  public void handleError(){
    activity.finish();
  }
}
