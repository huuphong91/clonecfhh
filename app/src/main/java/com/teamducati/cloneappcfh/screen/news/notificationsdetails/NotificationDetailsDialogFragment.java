package com.teamducati.cloneappcfh.screen.news.notificationsdetails;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.main.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

public class NotificationDetailsDialogFragment extends DialogFragment {

    private String mDataNotificatton = "";
    private View view;
    private Bundle bundle;
    private Dialog dialog;
    private String mUrl;
    private String mContent;
    private String mTitle;
    private TextView mTxtTilteDetailsNotifications;
    private TextView mTxtContentDetailsNotifications;
    private Button mBtnCloseDetailsNotification;
    private ImageView mImgDetailsNotifications;
    private Bitmap bitmapImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_details, container, false);
        initData();
        initMappingViewId();
        initEvent();
        return view;
    }

    private void initEvent() {
        mTxtTilteDetailsNotifications.setText(mTitle);
        mTxtContentDetailsNotifications.setText(mContent);
        Glide.with(getActivity())
                .load(mUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(mImgDetailsNotifications);

        mBtnCloseDetailsNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                sendNotification("1", "2",
//                        "https://feed.thecoffeehouse.com/content/images/2019/03/banner-caramel-macchiato.jpg");
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void initData() {
        bundle = this.getArguments();
        if (bundle != null) {
            mTitle = bundle.getString("title_notification");
            mContent = bundle.getString("content_notification");
            mUrl = bundle.getString("image_notification");
        }
    }

    private void initMappingViewId() {

        mTxtTilteDetailsNotifications = view.findViewById(R.id.txt_tilte_details_notifications);
        mTxtContentDetailsNotifications = view.findViewById(R.id.txt_content_details_notifications);
        mBtnCloseDetailsNotification = view.findViewById(R.id.btn_close_details_notification);
        mTxtTilteDetailsNotifications = view.findViewById(R.id.txt_tilte_details_notifications);
        mTxtContentDetailsNotifications = view.findViewById(R.id.txt_content_details_notifications);
        mBtnCloseDetailsNotification = view.findViewById(R.id.btn_close_details_notification);
        mImgDetailsNotifications = view.findViewById(R.id.img_details_notifications);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(1000,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void sendNotification(String messageBody, String titleMessege, String url) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        sendDataNotification(intent, titleMessege, messageBody, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "default_notification_channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.banner_caramel_macchiato);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity(), channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(titleMessege)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(largeIcon)
                                .bigLargeIcon(null))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        if(getBitmapUrl(url)!=null){
            Toast.makeText( getActivity(),"OK", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendDataNotification(Intent intent, String title, String content, String url) {
        intent.putExtra("firebase_id", "123456789fpt");
        intent.putExtra("firebase_title", title);
        intent.putExtra("firebase_content", content);
        intent.putExtra("firebase_url", url);
    }

    public Bitmap getBitmapUrl(String url) {
        Glide.with(getActivity())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmapImage=resource;
                    }
                });
        return bitmapImage;
    }
}