package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import io.reactivex.Single;

public interface AppRepository {

    Single<StoreResponeObject> getListStore();

}
