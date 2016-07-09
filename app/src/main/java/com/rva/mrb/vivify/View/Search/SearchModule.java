package com.rva.mrb.vivify.View.Search;

import android.util.Log;

import com.rva.mrb.vivify.Model.RealmService;
import com.rva.mrb.vivify.Model.SpotifyClient;
import com.rva.mrb.vivify.Model.SpotifyService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class SearchModule {

    final String SPOTIFY_URL = "https://api.spotify.com/v1/";
    final String TOKEN = "BQBKF_AEPXPdDrEMYP7WJ3feAyZZJsSLs8aOcOXvihnA1WTbfeS6bxAT5FcnJ5EoCtmEmUqfk0f-Wgjygbpw6so_FD7ZS2HaV7RuB2RuZbGyaUFDJYJAqeQ85vTdVj95riSkHKLapNvd";
    private final SearchActivity activity;

    public SearchModule(SearchActivity activity) {
        this.activity = activity;
    }

    @Provides
    SearchPresenter providesNewAlarmPresenterImpl(RealmService realmService) {
        return new SearchPresenterImpl(realmService);
    }

//    @Provides
//    public SpotifyClient providesSpotifyClient() {
//        return new SpotifyClient();
//    }

    @Provides
    public SpotifyService providesSpotifyService() {

        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        httpclient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", "Bearer " + TOKEN)
                        .build();
                Log.d("Request", request.headers()+"");

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpclient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(SpotifyService.class);
    }
}
