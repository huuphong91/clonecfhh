package com.teamducati.cloneappcfh.screen.news.notification;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.teamducati.cloneappcfh.data.local.repository.NotificationRepository;
import com.teamducati.cloneappcfh.entity.Notification;

import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
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
        mNotificationNews.setPresenter(this);
        this.lifecycleOwner = lifecycleOwner;
        this.application = application;
        mCompositeDisposable = new CompositeDisposable();
        notificationRepository = new NotificationRepository(application);
    }


    @Override
    public void start() {
        onAllListNotification();
    }

    @Override
    public void onAllListNotification() {
        notificationRepository.getAllNotification().observe(lifecycleOwner,
                new Observer<List<Notification>>() {
            @Override
            public void onChanged(@Nullable final List<Notification> listRoom) {
                mNotificationNews.getListNotification(listRoom);

            }
        });
    }

    @Override
    public void onInsertListNotification(Notification notification) {
        notificationRepository.insert(notification);
        new AlertDialog.Builder(application)
                .setTitle("Message Body Notification")
                .setMessage(notification.getContent())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();
    }
}
