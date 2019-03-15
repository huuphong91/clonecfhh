package com.teamducati.cloneappcfh.data.network.local.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import com.teamducati.cloneappcfh.data.network.local.NotificationNewsDatabase;
import com.teamducati.cloneappcfh.data.network.local.dao.NotificationDao;
import com.teamducati.cloneappcfh.entity.Notification;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class NotificationRepository implements NotificationLocalDataSource {
    public NotificationDao mNotificationDao;
    public LiveData<List<Notification>> mAllNotification;
    @SuppressLint("CheckResult")
    public NotificationRepository(Context application) {
        NotificationNewsDatabase db = NotificationNewsDatabase.getDatabase(application);
        mNotificationDao = db.notificationDao();
        mAllNotification = mNotificationDao.getAllNotification();
    }

    @Override
    public LiveData<List<Notification>> getAllNotification() {
        return mAllNotification;
    }
    
    @Override
    public void insert(Notification notification) {
        Completable.fromAction(() -> mNotificationDao.insert(notification))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    //Do nothing
        });
//        new insertAsyncTask(mNotificationDao).execute(notification);
    }

    @Override
    public void delete(Notification notification) {
        Completable.fromAction(() -> mNotificationDao.delete(notification))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    //Do nothing
                });
      //  new deleteDevicesAsyncTask(mNotificationDao).execute(notification);
    }

}
