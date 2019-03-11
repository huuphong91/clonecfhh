package com.teamducati.cloneappcfh.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  RetrofitConfig {
    public static final String BASE_URL = "https://api.thecoffeehouse.com/api/v2/";
    public RetrofitInterface requestInterface;
    public RetrofitInterface getInstanceRetrofit() {
         requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
        return requestInterface;

    }

}
