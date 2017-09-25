package com.pp.pikachu.api.managers;

import com.pp.pikachu.api.service.ApiService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class ApiManager {
  private final ApiService apiService;

  public ApiManager(ApiService apiService) {
    this.apiService = apiService;
  }

  public Observable<Object> getAirport() {
    return apiService.getAirport().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }
}
