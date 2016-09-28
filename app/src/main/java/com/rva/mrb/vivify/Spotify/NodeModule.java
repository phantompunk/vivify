package com.rva.mrb.vivify.Spotify;

import android.util.Log;

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

/**
 * Created by Bao on 9/24/16.
 */
@Module
public class NodeModule {
    final String NODE_URL = "https://192.168.1.8/";
    String accessToken;

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();
                Log.d("Request", request.headers() + "");
                return chain.proceed(request);
            }
        });
        OkHttpClient client = builder.build();
        return client;
    }

    @Provides
    @Singleton
    public NodeService getNodeService(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(NodeService.class);
    }
}

