package com.example.thecoffeehouse.store.views;

import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

public interface StoreView {
    void onStoreLoaded(StoreResponeObject responeObject);
    void onError(Throwable throwable);
}
