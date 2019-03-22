package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppRespositoryImp implements AppRepository {

    @Override
    public Single<StoreResponeObject> getListStore() {
        return ApiHandler.getInstance ().getAppApi ().getListStore ();
    }

    @Override
    public Observable<Order> getCartItem() {
        return ApiHandler.getInstance ().getAppApi ().getProduct ();
    }

    @Override
    public Observable<List<Category>> getCategory() {
        return ApiHandler.getInstance ().getAppApi ().getCategory ();
    }

}
