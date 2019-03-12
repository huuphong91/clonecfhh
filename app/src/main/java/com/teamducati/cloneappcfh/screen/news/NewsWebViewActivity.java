package com.teamducati.cloneappcfh.screen.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.teamducati.cloneappcfh.R;

public class NewsWebViewActivity extends AppCompatActivity {
    WebView webViewNews;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);
        initData();
        initMappingViewId();
        initWebView();
    }

    private void initData() {
        url = getIntent().getStringExtra("url_news");
    }

    private void initMappingViewId() {
        webViewNews =findViewById(R.id.web_view);
    }

    private void initWebView() {
        webViewNews.setWebViewClient(new MyBrowser());
        webViewNews.getSettings().setLoadsImagesAutomatically(true);
        webViewNews.getSettings().setJavaScriptEnabled(true);
        webViewNews.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webViewNews.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

