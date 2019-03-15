package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import io.reactivex.Single;

public class AppRespositoryImp implements AppRepository {

    @Override
    public Single<StoreResponeObject> getListStore() {
        return ApiHandler.getInstance().getAppApi().getListStore();
    }
}
