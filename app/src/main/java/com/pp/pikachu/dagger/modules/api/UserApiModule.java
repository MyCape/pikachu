package com.pp.pikachu.dagger.modules.api;

import com.pp.pikachu.api.managers.ApiManager;
import com.pp.pikachu.api.service.ApiService;
import com.pp.pikachu.dagger.scope.UserScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@UserScope @Module public class UserApiModule {

  @Provides @UserScope ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }

  @Provides @UserScope ApiManager provideApiManager(ApiService apiService) {
    return new ApiManager(apiService);
  }
}
