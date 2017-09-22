package com.pp.pikachu.dagger.modules.api;

import android.app.Application;
import com.pp.pikachu.BuildConfig;
import com.pp.pikachu.api.utils.AppConstants;
import com.pp.pikachu.api.utils.RxErrorHandlingCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton @Module public class ApiModule {

  @Provides @Singleton OkHttpClient provideOkHttpClient() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (BuildConfig.DEBUG) {
      final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(loggingInterceptor);
    }
    builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS).readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
    return builder.build();
  }

  @Provides @Singleton Retrofit provideRestAdapter(final Application application, final OkHttpClient okHttpClient) {
    final Retrofit.Builder builder = new Retrofit.Builder();
    builder.client(okHttpClient)
        .baseUrl(AppConstants.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create());
    return builder.build();
  }
}
