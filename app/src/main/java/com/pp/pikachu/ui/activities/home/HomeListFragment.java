package com.pp.pikachu.ui.activities.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.pp.pikachu.R;
import com.pp.pikachu.dagger.application.MainApplication;
import com.pp.pikachu.manager.SharedPreferenceManager;
import com.pp.pikachu.ui.activities.home.adapter.HomeListFragmentAdapter;
import javax.inject.Inject;

/**
 * Created by bry1337 on 22/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class HomeListFragment extends Fragment {

  Unbinder unbinder;

  @Inject SharedPreferenceManager sharedPreferenceManager;

  @BindView(R.id.rvAirportList) RecyclerView rvAirportList;

  public HomeListFragmentAdapter homeListFragmentAdapter;

  public String airportName;
  public HomeActivity activity;

  public static HomeListFragment newInstance() {
    return new HomeListFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainApplication.get(getActivity()).createHomeListFragmentComponent(this).inject(this);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    activity = (HomeActivity) context;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getFavorite();
    initListAdapter();
  }

  private void initListAdapter() {
    HomeMapPresenter homeMapPresenter = new HomeMapPresenter(this);
    homeMapPresenter.getAllRealmData();
    homeListFragmentAdapter =
        new HomeListFragmentAdapter(getActivity(), activity.airportsList, homeMapPresenter);
    rvAirportList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    rvAirportList.setAdapter(homeListFragmentAdapter);
  }

  private void getFavorite() {
    airportName = sharedPreferenceManager.getFavoriteAirport();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    MainApplication.get(getActivity()).releaseHomeListFragmentComponent();
  }
}
