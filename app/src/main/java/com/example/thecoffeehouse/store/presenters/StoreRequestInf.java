package com.example.thecoffeehouse.store.presenters;

import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface StoreRequestInf {
    @GET("/api/get_list_store")
    Single<StoreResponeObject> getListStore();
}
