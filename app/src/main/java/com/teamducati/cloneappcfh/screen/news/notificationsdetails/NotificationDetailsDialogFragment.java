package com.teamducati.cloneappcfh.screen.news.notificationsdetails;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class NotificationDetailsDialogFragment extends DialogFragment {

    private View view;
    private Dialog dialog;
    private String mUrl;
    private String mContent;
    private String mTitle;
    private TextView mTxtTilteDetailsNotifications;
    private TextView mTxtContentDetailsNotifications;
    private Button mBtnCloseDetailsNotification;
    private ImageView mImgDetailsNotifications;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_details, container, false);
        initData();
        initMappingViewId();
        initEvent();
        return view;
    }
    private void initEvent() {
        mTxtTilteDetailsNotifications.setText(mTitle);
        mTxtContentDetailsNotifications.setText(mContent);
        Glide.with(Objects.requireNonNull(getActivity()))
                .load(mUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(mImgDetailsNotifications);
        mBtnCloseDetailsNotification.setOnClickListener(view -> dialog.dismiss());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void initData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(Constants.KEY_BUNDLE_FIREBASE_TITLE);
            mContent = bundle.getString(Constants.KEY_BUNDLE_FIREBASE_CONTENT);
            mUrl = bundle.getString(Constants.KEY_BUNDLE_FIREBASE_IMAGE_URL);
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
            Objects.requireNonNull(dialog.getWindow()).setLayout(1000,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}