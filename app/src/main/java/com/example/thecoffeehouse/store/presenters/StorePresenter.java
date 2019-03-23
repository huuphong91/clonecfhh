package com.example.thecoffeehouse.store.presenters;

public interface StorePresenter {
    void loadListStore();
    void loadListStoreFromDatabase();
    void onDestroy();
    void onStop();
}
