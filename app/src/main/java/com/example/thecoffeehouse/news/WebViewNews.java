package com.example.thecoffeehouse.news;

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
import androidx.annotation.RawRes;
import androidx.fragment.app.DialogFragment;

public class WebViewNews extends DialogFragment {
    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;
    private WebView webView;
    private ResponseNews news;
    private TextView txtTitle;
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
        webView = rootView.findViewById(R.id.webView);
        webView.loadUrl(news.getUrl());
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light);
    }
    private void initView(View view) {


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
}
