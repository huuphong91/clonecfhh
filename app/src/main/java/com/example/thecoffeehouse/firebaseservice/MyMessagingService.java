package com.example.thecoffeehouse.firebaseservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.main.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyMessagingService extends FirebaseMessagingService {

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private AppRespositoryImp appRespositoryImp;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),
                remoteMessage.getData().get("image"));
        appRespositoryImp = new AppRespositoryImp(getApplication());
        appRespositoryImp.insertNotification(
                new Notification(remoteMessage.getNotification().getTitle()
                        , remoteMessage.getNotification().getBody()
                        , remoteMessage.getData().get("image")));
    }

    public void showNotification(String tittle, String messege, String linkUrl) {

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        Bitmap androidImage = getBitmapFromURL(linkUrl);
        style.setSummaryText(messege);
        style.bigPicture(androidImage);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Intent intent = new Intent(this, MainActivity.class);
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this, 1, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(tittle)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(messege)
                .setContentText(messege)
                .setContentIntent(updatePendingIntent)
                .setStyle(style);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

}
