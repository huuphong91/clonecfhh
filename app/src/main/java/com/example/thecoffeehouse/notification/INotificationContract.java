package com.example.thecoffeehouse.notification;

import android.app.Application;
import android.content.Context;

import com.example.thecoffeehouse.data.model.notification.Notification;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface INotificationContract {
    interface View{
        void onLoadSucess(String result);
        void onLoadFail(String result);
        Application getApplication();
        Context getContext();
    }

    interface Presenter{
        LiveData<List<Notification>> loadNotification();
    }
}
