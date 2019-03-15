package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.store.StoreResponeObject;
import com.example.thecoffeehouse.data.model.product.Order;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface AppApi {

    @GET("api/v2/menu")
    Observable<Order> getProduct();

    @GET("api/get_list_store")
    Single<StoreResponeObject> getListStore();
}
