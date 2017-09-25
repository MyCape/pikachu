package com.pp.pikachu.ui.activities.home;

import android.support.test.runner.AndroidJUnit4;
import com.pp.pikachu.dagger.application.MainApplication;
import com.pp.pikachu.models.Airports;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by bry1337 on 25/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@RunWith(AndroidJUnit4.class) public class HomeMapPresenterTest {

  private List<Airports> airportsList;

  @Before public void setUp() throws Exception {
    Realm.init(MainApplication.get());
    airportsList = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Airports> airportsRealmResults = realm.where(Airports.class).findAll();
    airportsRealmResults.load();
    for (Airports airports : airportsRealmResults) {
      airportsList.add(airports);
    }
  }

  @Test public void getAllRealmData() throws Exception {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Airports> airportsRealmResults = realm.where(Airports.class).findAll();
    assertTrue(airportsRealmResults.load());
  }

  @Test public void isAirportsListNotNull() {
    assertNotNull(airportsList);
  }

  @Test public void isAirportListNotEmpty() {
    assertTrue(airportsList.size() > 0);
  }
}