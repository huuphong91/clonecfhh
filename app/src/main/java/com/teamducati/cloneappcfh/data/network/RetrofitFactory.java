package com.teamducati.cloneappcfh.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.teamducati.cloneappcfh.utils.Constants.BASE_URL;

public class RetrofitFactory {

    private static RetrofitApi requestInterfaceInstance;

    public static RetrofitApi getInstanceRetrofitInterface() {

        if (requestInterfaceInstance == null) {
            requestInterfaceInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitApi.class);
        }
        return requestInterfaceInstance;
    }
}
