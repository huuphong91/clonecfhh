package com.teamducati.cloneappcfh.screen.news.notification.firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.Notification;
import com.teamducati.cloneappcfh.screen.main.MainActivity;
import com.teamducati.cloneappcfh.screen.news.notification.NoticationContract;
import com.teamducati.cloneappcfh.screen.news.notification.NotificationPresenter;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

@SuppressWarnings("unchecked")
public class NotificationFirebaseMessagingService extends FirebaseMessagingService implements NoticationContract.View {

    private static final String TAG = "MyFirebaseMsgService";
    private Bitmap bitmapImage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NoticationContract.Presenter mPresenter = new NotificationPresenter(this, getApplicationContext());
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            scheduleJob();
        }
        if (remoteMessage.getNotification() != null) {

            sendNotification(remoteMessage.getNotification().getBody(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getData().get(Constants.FIREBASE_IMAGE_URL));

            mPresenter.onInsertListNotification(new Notification(
                    remoteMessage.getData().get(Constants.FIREBASE_TITLE),
                    remoteMessage.getData().get(Constants.FIREBASE_CONTENT),
                    new Date().toString(),
                    remoteMessage.getData().get(Constants.FIREBASE_IMAGE_URL)
            ));

        }

    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer();
    }

    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(NotificationFirebaseWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void sendRegistrationToServer() {
        // TODO: Implement this method to send token to your app server.
    }


    private void sendNotification(String messageBody, String titleMessege, String url) {
        Intent intent = new Intent(this, MainActivity.class);
        sendDataNotification(intent, titleMessege, messageBody, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "default_notification_channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        try {
            Bitmap bitmapImageUrl = Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();
            bitmapImage = Bitmap.createScaledBitmap(bitmapImageUrl,
                    (int) (bitmapImageUrl.getWidth() * 0.5),
                    (int) (bitmapImageUrl.getHeight() * 0.5), true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_tch)
                        .setLargeIcon(bitmapImage)
                        .setContentTitle(titleMessege)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmapImage)
                                .bigLargeIcon(null))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    public void sendDataNotification(Intent intent, String title, String content, String url) {
        intent.putExtra(Constants.FIREBASE_ID, "123456789fpt");
        intent.putExtra(Constants.FIREBASE_TITLE, title);
        intent.putExtra(Constants.FIREBASE_CONTENT, content);
        intent.putExtra(Constants.FIREBASE_IMAGE_URL, url);
    }

    @Override
    public void getListNotification(List<Notification> arrayList) {

    }
}
