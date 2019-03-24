package com.example.thecoffeehouse.data.model.notification;

import com.example.thecoffeehouse.data.model.store.Store;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface NotificationDAO {

    @Query("SELECT * FROM notification")
    LiveData<List<Notification>> getListNotification();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertNotification(Notification notification);
}
