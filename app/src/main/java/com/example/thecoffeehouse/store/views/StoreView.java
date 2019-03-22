package com.example.thecoffeehouse.store.views;

import android.app.Activity;

import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

public interface StoreView {
    void onStoreLoaded(StoreResponeObject responeObject);
    void onStoreLoaded(List<Store> listStore);
    void onError(Throwable throwable);
    Activity getActivity();
}
