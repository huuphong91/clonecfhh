package com.teamducati.cloneappcfh.screen.news.newsdetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class NewsWebViewDetailsDialogFragment extends DialogFragment {
    private WebView webViewNews;
    private ImageView imgBackWebView;
    private TextView txtTitleWebView;
    private String mUrl = "";
    private String mTitle = "";
    private View view;
    private Dialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_web_view, container, false);
        initData();
        initMappingViewId();
        initEvent();
        initWebView();
        return view;
    }

    private void initEvent() {
        imgBackWebView.setOnClickListener(view -> dialog.dismiss());
        txtTitleWebView.setText(mTitle);
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
            mUrl = bundle.getString(Constants.KEY_BUNDLE_WEB_VIEW_URL);
            mTitle = bundle.getString(Constants.KEY_BUNDLE_WEB_VIEW_TITLE);

        }
    }

    private void initMappingViewId() {
        webViewNews = view.findViewById(R.id.web_view);
        imgBackWebView = view.findViewById(R.id.img_back_web_view);
        txtTitleWebView = view.findViewById(R.id.txt_title_web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webViewNews.setWebViewClient(new MyBrowser());
        webViewNews.getSettings().setLoadsImagesAutomatically(true);
        webViewNews.getSettings().setJavaScriptEnabled(true);
        webViewNews.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webViewNews.loadUrl(mUrl);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}