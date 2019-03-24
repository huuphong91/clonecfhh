package com.teamducati.cloneappcfh.screen.news.notification;

import android.content.Context;

import com.teamducati.cloneappcfh.data.local.repository.NotificationRepository;
import com.teamducati.cloneappcfh.entity.Notification;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class NotificationPresenter implements NoticationContract.Presenter {
    private  NoticationContract.View mNotificationNews;
    private CompositeDisposable mCompositeDisposable;
    private Disposable disposable;
    private NotificationRepository notificationRepository;
    private LifecycleOwner lifecycleOwner;
    private Context application;
    public NotificationPresenter(NoticationContract.View mNotificationNews, Context application ,
                                 LifecycleOwner lifecycleOwner) {
        this.mNotificationNews = mNotificationNews;
        this.lifecycleOwner = lifecycleOwner;
        this.application = application;
        mCompositeDisposable = new CompositeDisposable();
        notificationRepository = new NotificationRepository(application);
    }

    public NotificationPresenter(NoticationContract.View mNotificationNews, Context application ) {
        this.mNotificationNews = mNotificationNews;
        this.application = application;
        mCompositeDisposable = new CompositeDisposable();
        notificationRepository = new NotificationRepository(application);
    }

    public NotificationPresenter() {
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
