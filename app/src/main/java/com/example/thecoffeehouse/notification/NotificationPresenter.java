package com.example.thecoffeehouse.notification;

import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.update.editbirthday.IEditBirhDayContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public class NotificationPresenter implements INotificationContract.Presenter {

    private INotificationContract.View callback;
    private AppRespositoryImp appRespositoryImp;
    private LiveData<List<Notification>> mAllNotification;


    public NotificationPresenter(INotificationContract.View callback){
        this.callback = callback;
        appRespositoryImp = new AppRespositoryImp(callback.getApplication());
    }


    @Override
    public LiveData<List<Notification>> loadNotification() {
        mAllNotification = appRespositoryImp.getNotification((LifecycleOwner) callback.getContext());
        callback.onLoadSucess("OKE");
        return mAllNotification;
    }
}
