package com.example.thecoffeehouse.data.model.entity;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface NewsDataDao {
   @Query(" SELECT * FROM newsdata")
    Single<List<ResponseNews>> getNews();

    @Query("DELETE FROM newsdata")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertNews(ResponseNews responsenews);

}
