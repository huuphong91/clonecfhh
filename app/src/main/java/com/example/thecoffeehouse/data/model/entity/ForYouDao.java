package com.example.thecoffeehouse.data.model.entity;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface ForYouDao {
    @Query("SELECT * FROM foryounews")
    Single<List<ResponseForYou>> getForYou();

    @Query("DELETE FROM foryounews")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertForYouNews(ResponseForYou responseForYou);
}
