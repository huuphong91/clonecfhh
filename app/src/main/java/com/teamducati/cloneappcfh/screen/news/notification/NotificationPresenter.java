package com.teamducati.cloneappcfh.screen.news.notification;

import android.content.Context;

import com.teamducati.cloneappcfh.data.local.repository.NotificationRepository;
import com.teamducati.cloneappcfh.entity.Notification;

import androidx.lifecycle.LifecycleOwner;

@SuppressWarnings("ALL")
public class NotificationPresenter implements NoticationContract.Presenter {
    private NoticationContract.View mNotificationNews;
    private NotificationRepository notificationRepository;
    private LifecycleOwner lifecycleOwner;
    private Context mContext;

    public NotificationPresenter(NoticationContract.View mNotificationNews, Context context,
                                 LifecycleOwner lifecycleOwner) {
        this.mNotificationNews = mNotificationNews;
        this.lifecycleOwner = lifecycleOwner;
        this.mContext = context;
        notificationRepository = new NotificationRepository(mContext);
    }

    //constructor insert firebase notifications
    public NotificationPresenter(NoticationContract.View mNotificationNews, Context context) {
        this.mNotificationNews = mNotificationNews;
        this.mContext = context;
        notificationRepository = new NotificationRepository(mContext);
    }

    @Override
    public void onAllListNotification() {
        notificationRepository.getAllNotification().observe(lifecycleOwner,
                listRoom -> mNotificationNews.getListNotification(listRoom));
    }

    @Override
    public void onInsertListNotification(Notification notification) {
        notificationRepository.insert(notification);

    }

    @Override
    public void takeView(Object view) {

    }

    @Override
    public void dropView() {

    }
}
