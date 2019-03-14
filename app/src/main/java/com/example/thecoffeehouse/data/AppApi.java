package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.product.Order;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AppApi {

    @GET("api/v2/menu")
    Observable<Order> getProduct();
}
