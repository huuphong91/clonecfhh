package com.teamducati.cloneappcfh.screen.news.newsdetails;

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

import androidx.fragment.app.DialogFragment;

public class NewsWebViewDetailsDialogFragment extends DialogFragment {
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
        view = inflater.inflate(R.layout.fragment_news_web_view, container, false);
        initData();
        initMappingViewId();
        initEvent();
        initWebView();
        return view;
    }

    private void initEvent() {
        imgBackWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txtTitleWebView.setText(mTitle);
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
            mUrl = bundle.getString("url");
            mTitle = bundle.getString("title");

        }
    }

    private void initMappingViewId() {
        webViewNews = view.findViewById(R.id.web_view);
        imgBackWebView=view.findViewById(R.id.img_back_web_view);
        txtTitleWebView=view.findViewById(R.id.txt_title_web_view);
    }

    private void initWebView() {
        webViewNews.setWebViewClient(new MyBrowser());
        webViewNews.getSettings().setLoadsImagesAutomatically(true);
        webViewNews.getSettings().setJavaScriptEnabled(true);
        webViewNews.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webViewNews.loadUrl(mUrl);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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