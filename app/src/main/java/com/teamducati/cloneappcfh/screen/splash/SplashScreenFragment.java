package com.teamducati.cloneappcfh.screen.splash;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.main.MainActivity;

import androidx.fragment.app.DialogFragment;

public class SplashScreenFragment extends DialogFragment {
    private WebView webViewNews;
    private ImageView imgBackWebView;
    private TextView txtTitleWebView;
    private String mUrl = "";
    private String mTitle = "";
    private View view;
    private Bundle bundle;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sqlash_srcreen, container, false);
        initData();
        initMappingViewId();
        initEvent();
        return view;
    }

    private void initEvent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                dismiss();
            }
        }, 3000);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void initData() {

    }

    private void initMappingViewId() {

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


}