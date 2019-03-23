package com.example.thecoffeehouse.news.webview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class WebViewForYou extends DialogFragment  {
    private WebView webView;
    private ResponseForYou for_you;
    private TextView txtTitle;
    private ImageView imgbacknews;
    private Button btn_for_news;
    private BottomNavigationView navigation;
    private View rootView;
    public static WebViewForYou newInstance(ResponseForYou for_you) {
        WebViewForYou fragment_for_you = new WebViewForYou();
        Bundle bundle = new Bundle();
        bundle.putSerializable("News",for_you );
        fragment_for_you.setArguments(bundle);
        return fragment_for_you;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments() != null){
           for_you = (ResponseForYou) getArguments().getSerializable("News");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_item_detail_news, container, false);
        txtTitle=rootView.findViewById(R.id.txtTitle);
        btn_for_news=rootView.findViewById(R.id.btn_thu_ngay);
        txtTitle.setText(for_you.getTitle());
        navigation = getActivity().findViewById(R.id.navigation);
        webView = rootView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(for_you.getUrl());
        imgbacknews=rootView.findViewById(R.id.imgBackNews);
        imgbacknews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;


    }
//    public void submit_change_fragment()
//    {
//        dismiss();
//        navigation.setSelectedItemId(R.id.navigation_order);
//
//    }
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
//        btn_for_news.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit_change_fragment();
//            }
//        });

    }
}
