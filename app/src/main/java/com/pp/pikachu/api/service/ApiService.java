package com.pp.pikachu.api.service;

import com.pp.pikachu.api.utils.AppConstants;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@SuppressWarnings("PMD") public interface ApiService {

  @Streaming @GET(AppConstants.API_URL_JSON) Observable<Object> getAirport();
}
