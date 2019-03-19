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
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;

import androidx.fragment.app.DialogFragment;

public class NotificationDetailsDialogFragment extends DialogFragment {

    private String mDataNotificatton = "";
    private View view;
    private Bundle bundle;
    private Dialog dialog;
    private Button mBtnCloseDetailsNotification;
    private TextView mTxtContentDetailsNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_details, container, false);
        initData();
        initMappingViewId();
        initEvent();
        return view;
    }

    private void initEvent() {
        mTxtContentDetailsNotification.setText(mDataNotificatton);
        mBtnCloseDetailsNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
            mDataNotificatton = bundle.getString("data_notification");

        }
    }

    private void initMappingViewId() {

        mBtnCloseDetailsNotification = view.findViewById(R.id.btn_close_details_notification);
        mTxtContentDetailsNotification = view.findViewById(R.id.txt_content_details_notification);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            dialog.getWindow().setLayout(800,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


}