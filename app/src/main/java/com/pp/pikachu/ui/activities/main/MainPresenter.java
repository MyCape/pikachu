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

  private void handleProcessing(Object object) {
    JsonObject jsonObject = new Gson().toJsonTree(object).getAsJsonObject();
    JSONArray jsonArray = new JSONArray();
    List<String> keysList = activity.dataParser.parseKeys(jsonObject);
    for (int y = 0; y < jsonObject.size(); y++) {
      jsonArray.put(jsonObject.get(keysList.get(y)));
    }
    processSaving(jsonArray);
  }

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

  public void goToHomePage() {
    activity.appActivityManager.launchHomePage(activity);
  }

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

  public void handleError(){
    activity.finish();
  }
}
