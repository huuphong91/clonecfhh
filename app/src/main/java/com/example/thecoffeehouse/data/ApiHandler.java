package com.example.thecoffeehouse.data;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static ApiHandler sInstance;
    private AppApi mAppApi;
    private static final String BASE_URL = "https://api.thecoffeehouse.com/";

    private ApiHandler() {
        mAppApi = new Retrofit
                .Builder()
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
