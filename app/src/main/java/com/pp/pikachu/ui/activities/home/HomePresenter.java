package com.pp.pikachu.ui.activities.home;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.pp.pikachu.R;
import com.pp.pikachu.api.managers.ApiManager;
import com.pp.pikachu.ui.activities.BasePresenter;
import com.pp.pikachu.ui.activities.home.adapter.HomeAdapter;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomePresenter extends BasePresenter {

  private final HomeActivity activity;

  private final ApiManager apiManager;

  public HomePresenter(HomeActivity activity, ApiManager apiManager) {
    this.activity = activity;
    this.apiManager = apiManager;
  }

  @Override public Activity getActivity() {
    return activity;
  }

  @Override public AlertDialog getAlertDialog() {
    return activity.alertDialog;
  }

  public void setupTabListener(TabLayout tlHome, HomeAdapter homeAdapter) {
    tlHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
          HomeListFragment homeListFragment = (HomeListFragment) homeAdapter.getItem(0);
          homeListFragment.homeListFragmentAdapter.notifyDataSetChanged();
        }
      }

      @Override public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }
}
