package com.pp.pikachu.ui.activities.home;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pp.pikachu.R;
import com.pp.pikachu.api.utils.AppConstants;
import com.pp.pikachu.models.Airports;
import com.pp.pikachu.ui.utils.OnItemClickListener;
import com.pp.pikachu.ui.utils.OnRemoveFavoriteListener;
import com.pp.pikachu.ui.utils.OnSaveFavoriteListener;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeMapPresenter
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener, OnItemClickListener, OnSaveFavoriteListener, OnRemoveFavoriteListener {

  public GoogleApiClient mGoogleApiClient;
  public LocationRequest mLocationRequest;

  private double currentLatitude;
  private double currentLongitude;

  private HomeMapFragment homeMapFragment;
  private HomeListFragment homeListFragment;

  private List<Airports> airportsList;

  public HomeMapPresenter(HomeMapFragment homeMapFragment) {
    this.homeMapFragment = homeMapFragment;
    airportsList = homeMapFragment.activity.airportsList;
  }

  public HomeMapPresenter(HomeListFragment homeMapFragment) {
    this.homeListFragment = homeMapFragment;
    airportsList = homeMapFragment.activity.airportsList;
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    if (ActivityCompat.checkSelfPermission(homeMapFragment.getActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(homeMapFragment.getActivity(),
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(homeMapFragment.getActivity(), new String[] {
          Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
      }, HomeMapFragment.REQUEST_PERMISSION_LOCATION);
      return;
    }
    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

    if (location == null) {
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
          this);
    } else {
      //If everything went fine lets get latitude and longitude
      currentLatitude = location.getLatitude();
      currentLongitude = location.getLongitude();
      homeMapFragment.initCurrentLocation(currentLatitude, currentLongitude);
      sortLocations();
      Toast.makeText(homeMapFragment.activity, homeMapFragment.activity.getString(R.string.sorted),
          Toast.LENGTH_LONG).show();
    }
  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    if (connectionResult.hasResolution()) {
      try {
        connectionResult.startResolutionForResult(homeMapFragment.getActivity(),
            HomeMapFragment.CONNECTION_FAILURE_RESOLUTION_REQUEST);
      } catch (IntentSender.SendIntentException e) {
        e.printStackTrace();
      }
    } else {
      Log.e("Error",
          "Location services connection failed with code " + connectionResult.getErrorCode());
    }
  }

  @Override public void onLocationChanged(Location location) {
    currentLatitude = location.getLatitude();
    currentLongitude = location.getLongitude();
    homeMapFragment.initCurrentLocation(currentLatitude, currentLongitude);
  }

  @Override public void onItemClick(Object object) {
    Airports airports = (Airports) object;
    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
    intent.putExtra(SearchManager.QUERY, airports.getName());
    homeListFragment.getActivity().startActivity(intent);
  }

  @Override public void onSave(String favorite, int pos) {
    homeListFragment.sharedPreferenceManager.saveFavoriteAirport(favorite);
    homeListFragment.airportName = favorite;
    homeListFragment.homeListFragmentAdapter.notifyItemChanged(pos);
  }

  @Override public void onRemove(int pos) {
    homeListFragment.airportName = StringUtils.EMPTY;
    homeListFragment.sharedPreferenceManager.clearFavorite();
    homeListFragment.homeListFragmentAdapter.notifyItemChanged(pos);
  }

  public void getAllRealmData() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Airports> airportList = realm.where(Airports.class).findAllAsync();
    airportList.load();
    this.airportsList.clear();
    for (Airports airports : airportList) {
      this.airportsList.add(airports);
    }
  }

  public void processMarker(GoogleMap googleMap) {
    LatLng airport;
    for (int i = 0; i < this.airportsList.size(); i++) {
      airport = new LatLng(Double.parseDouble(this.airportsList.get(i).getLatitude()),
          Double.parseDouble(this.airportsList.get(i).getLongitude()));
      if (!airportsList.get(i)
          .getName()
          .equals(homeMapFragment.sharedPreferenceManager.getFavoriteAirport())) {
        googleMap.addMarker(
            new MarkerOptions().position(airport).title(this.airportsList.get(i).getCity()));
      } else {
        googleMap.addMarker(new MarkerOptions().position(airport)
            .title(this.airportsList.get(i).getCity())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
      }
    }
  }

  public String getFavorite() {
    return homeListFragment.airportName;
  }

  public void sortLocations() {
    Location myCurrentLocation = new Location(AppConstants.CURRENT_LOCATION);
    myCurrentLocation.setLatitude(currentLatitude);
    myCurrentLocation.setLongitude(currentLongitude);
    Collections.sort(airportsList, (airports, t1) -> {
      Location location1 = new Location(airports.getName());
      location1.setLatitude(Double.parseDouble(airports.getLatitude()));
      location1.setLongitude(Double.parseDouble(airports.getLongitude()));
      Location location2 = new Location(t1.getName());
      location2.setLatitude(Double.parseDouble(t1.getLatitude()));
      location2.setLongitude(Double.parseDouble(t1.getLongitude()));
      return (int) (location1.distanceTo(myCurrentLocation) - location2.distanceTo(
          myCurrentLocation));
    });
  }

  public double getCurrentLatitude() {
    return currentLatitude;
  }

  public double getCurrentLongitude() {
    return currentLongitude;
  }
}
