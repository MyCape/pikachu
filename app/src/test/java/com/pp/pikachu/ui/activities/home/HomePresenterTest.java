package com.pp.pikachu.ui.activities.home;

import com.pp.pikachu.api.managers.ApiManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by bry1337 on 25/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@RunWith(MockitoJUnitRunner.class) public class HomePresenterTest {

  @Mock HomeActivity homeActivity = mock(HomeActivity.class);
  @Mock ApiManager apiManager;

  private HomePresenter homePresenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    homePresenter = new HomePresenter(homeActivity, apiManager);
  }

  @Test public void isActivityNotNull() throws Exception {
    assertTrue(homePresenter != null);
  }
}
