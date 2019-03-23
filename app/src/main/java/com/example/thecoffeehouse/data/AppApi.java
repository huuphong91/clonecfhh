package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface AppApi {

    @GET("api/v2/menu")
    Observable<Order> getProduct();

    @GET("api/get_list_store")
    Single<StoreResponeObject> getListStore();

    @GET("api/v2/news")
   Single<List<ResponseNews>> getNews();

    @GET("api/v2/news_promotion")
    Single<List<ResponseForYou>> getForYou();

    @GET("api/v2/category/web")
    Observable<List<Category>> getCategory();

}
