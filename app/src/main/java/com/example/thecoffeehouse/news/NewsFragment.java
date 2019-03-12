package com.example.thecoffeehouse.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
    }
}
