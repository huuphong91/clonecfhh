package com.teamducati.cloneappcfh.data.network.local.dao;

import com.teamducati.cloneappcfh.entity.Notification;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NotificationDao {

    @Query("SELECT * from notification_table ")
    LiveData<List<Notification>> getAllNotification();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Notification notification);

    @Query("DELETE FROM notification_table")
    void deleteAll();

    @Delete
    void delete(Notification notification);


}
