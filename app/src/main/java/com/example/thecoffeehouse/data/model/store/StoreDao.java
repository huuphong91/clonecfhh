package com.example.thecoffeehouse.data.model.store;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface StoreDao {

    @Query("SELECT * FROM store")
    Single<List<Store>> getListStore();

    @Query("DELETE FROM store")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertStore(Store store);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertStores(List<Store> stores);
}
