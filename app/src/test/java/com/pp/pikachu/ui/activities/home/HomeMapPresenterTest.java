package com.pp.pikachu.ui.activities.home;

import com.pp.pikachu.manager.SharedPreferenceManager;
import com.pp.pikachu.models.Airports;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bry1337 on 25/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@RunWith(MockitoJUnitRunner.class) public class HomeMapPresenterTest {

  @Mock HomeMapPresenter homeMapPresenter = mock(HomeMapPresenter.class);
  @Mock SharedPreferenceManager sharedPreferenceManager = mock(SharedPreferenceManager.class);

  private Airports airports = mock(Airports.class);

  @Before public void setUp() throws Exception {

  }

  @Test public void isAirportsMockable() {
    when(airports.getName()).thenReturn("Lowell");
    assertEquals(airports.getName(), "Lowell");
  }

  @Test public void isItemClickInterfaceMockable() {
    homeMapPresenter.onItemClick(airports);
    verify(homeMapPresenter).onItemClick(airports);
  }

  @Test public void saveToSharedPreference() {
    sharedPreferenceManager.saveFavoriteAirport(airports.getName());
    verify(sharedPreferenceManager).saveFavoriteAirport(airports.getName());
  }

  @Test public void removeFromSharedPreference() {
    sharedPreferenceManager.clearFavorite();
    verify(sharedPreferenceManager).clearFavorite();
  }
}