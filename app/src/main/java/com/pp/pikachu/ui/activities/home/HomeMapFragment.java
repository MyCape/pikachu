package com.pp.pikachu.ui.activities.home;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.pp.pikachu.R;
import com.pp.pikachu.dagger.application.MainApplication;
import com.pp.pikachu.manager.SharedPreferenceManager;
import javax.inject.Inject;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeMapFragment extends Fragment implements OnMapReadyCallback {

  @Inject SharedPreferenceManager sharedPreferenceManager;

  Unbinder unbinder;

  //Define a request code to send to Google Play services
  public static final int REQUEST_PERMISSION_LOCATION = 1000;
  public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

  public HomeActivity activity;

  private GoogleMap googleMap;
  private HomeMapPresenter homeMapPresenter;

  public static HomeMapFragment newInstance() {
    return new HomeMapFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.get(getActivity()).createHomeMapFragment(this).inject(this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_map, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    activity = (HomeActivity) context;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initiateMapViewMapPresenter(view, savedInstanceState);
    initiateGoogleApiClient();
    getPresenterRealmData();
    initiateLocationRequest();
    initCurrentLocation(homeMapPresenter.getCurrentLatitude(), homeMapPresenter.getCurrentLongitude());
  }

  @Override public void onResume() {
    super.onResume();
    //Now lets connect to the API
    homeMapPresenter.mGoogleApiClient.connect();
  }

  @Override public void onPause() {
    super.onPause();

    //Disconnect from API onPause()
    if (homeMapPresenter.mGoogleApiClient.isConnected()) {
      LocationServices.FusedLocationApi.removeLocationUpdates(homeMapPresenter.mGoogleApiClient, homeMapPresenter);
      homeMapPresenter.mGoogleApiClient.disconnect();
    }
  }

  @Override public void onMapReady(GoogleMap map) {
    // Add a marker in Sydney, Australia,
    // and move the map's camera to the same location.
    googleMap = map;
    homeMapPresenter.processMarker(googleMap);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    MainApplication.get(getActivity()).releaseHomeMapFragment();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_PERMISSION_LOCATION) {
      if (grantResults.length == 2
          && grantResults[0] != PackageManager.PERMISSION_GRANTED
          && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(getActivity(), getString(R.string.handle_location_service), Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void initiateLocationRequest() {
    // Create the LocationRequest object
    homeMapPresenter.mLocationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(10 * 1000)        // 10 seconds, in milliseconds
        .setFastestInterval(1 * 1000); // 1 second, in milliseconds
  }

  private void getPresenterRealmData() {
    homeMapPresenter.getAllRealmData();
  }

  private void initiateGoogleApiClient() {
    homeMapPresenter.mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
        // The next two lines tell the new client that “this” current class will handle connection stuff
        .addConnectionCallbacks(homeMapPresenter).addOnConnectionFailedListener(homeMapPresenter)
        //fourth line adds the LocationServices API endpoint from GooglePlayServices
        .addApi(LocationServices.API).build();
  }

  private void initiateMapViewMapPresenter(View view, @Nullable Bundle savedInstanceState) {
    MapView mapView = view.findViewById(R.id.map);
    mapView.onCreate(savedInstanceState);
    mapView.onResume();
    mapView.getMapAsync(this);
    homeMapPresenter = new HomeMapPresenter(this);
  }

  public void initCurrentLocation(double currentLat, double currentLong) {
    if (currentLat != 0 && currentLong != 0) {
      LatLng latLng = new LatLng(currentLat, currentLong);
      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
      this.googleMap.animateCamera(cameraUpdate);
    }
  }
}
