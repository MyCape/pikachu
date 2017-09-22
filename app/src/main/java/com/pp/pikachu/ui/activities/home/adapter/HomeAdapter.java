package com.pp.pikachu.ui.activities.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.pp.pikachu.R;
import com.pp.pikachu.ui.activities.home.HomeListFragment;
import com.pp.pikachu.ui.activities.home.HomeMapFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeAdapter extends FragmentPagerAdapter {

  private Context context;
  private List<Fragment> fragments;
  private FragmentManager fragmentManager;

  public HomeAdapter(FragmentManager fm, final Context context) {
    super(fm);
    this.fragmentManager = fm;
    this.context = context;
    createFragmentList();
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }

  private void createFragmentList() {
    fragments = new ArrayList<>();
    HomeMapFragment homeMapFragment = HomeMapFragment.newInstance();
    fragments.add(HomeListFragment.newInstance());
    fragments.add(homeMapFragment);
  }

  @Override public CharSequence getPageTitle(int position) {
    String title;
    switch (position) {
      case 0:
        title = context.getString(R.string.list);
        break;
      case 1:
        title = context.getString(R.string.map);
        break;
      default:
        title = context.getString(R.string.list);
        break;
    }
    return title;
  }
}
