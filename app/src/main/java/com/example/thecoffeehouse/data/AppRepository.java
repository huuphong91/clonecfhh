package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import io.reactivex.Observable;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface AppRepository {

    Single<StoreResponeObject> getListStore();

    Single<List<Store>> getListStoreFromDatabase();

    Flowable<List<Long>> loadApiToDatabase();

    Observable<Order> getProduct();

    Observable<List<Category>> getCategory();

    Observable<Order> getCartItem();

    Single<List<ResponseNews>> getListForNewsFromDatabase();

    Single<List<ResponseNews>> getNews();

    Flowable<Long> loadApiForNewsToDatabase();
    Single<List<ResponseForYou>> getListNewsFromDatabase();

    Single<List<ResponseForYou>> getForYou();

    Flowable<Long> loadApiNewsToDatabase();
    void insertNotification(Notification notification);

}
