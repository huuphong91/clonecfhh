package com.example.thecoffeehouse.data;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static ApiHandler sInstance;
    private AppApi mAppApi;
    private static final String BASE_URL = "https://api.thecoffeehouse.com/";

    private ApiHandler() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        mAppApi = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(AppApi.class);
    }

    public static ApiHandler getInstance() {
        if (sInstance == null) {
            sInstance = new ApiHandler();
        }
        return sInstance;
    }

    public AppApi getAppApi() {
        return mAppApi;
    }


}
