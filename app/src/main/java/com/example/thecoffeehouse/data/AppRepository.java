package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface AppRepository {

    Single<StoreResponeObject> getListStore();

    Observable<Order> getCartItem();

    Observable<List<Category>> getCategory();
}
