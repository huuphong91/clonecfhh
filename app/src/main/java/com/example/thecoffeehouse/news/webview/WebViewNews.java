package com.example.thecoffeehouse.news.webview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class WebViewNews extends DialogFragment  {
    private WebView webView;
    private ResponseNews news;
    private TextView txtTitle;
    ImageView imgbacknews;
    public static WebViewNews newInstance(ResponseNews news) {
        WebViewNews fragment = new WebViewNews();
        Bundle bundle = new Bundle();
        bundle.putSerializable("News", news);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments() != null){
            news = (ResponseNews) getArguments().getSerializable("News");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_item_detail_news, container, false);
        txtTitle=rootView.findViewById(R.id.txtTitle);
        txtTitle.setText(news.getTitle());
        webView = rootView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(news.getUrl());
        imgbacknews=rootView.findViewById(R.id.imgBackNews);
        imgbacknews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dismiss();
            }
        });
        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar);
    }
    private void initView(View view) {


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
}
