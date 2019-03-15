package com.example.thecoffeehouse.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.news.adapter.NewsAdapter;
import com.example.thecoffeehouse.news.adapter.NewsAdapterNews;
import com.example.thecoffeehouse.news.presenter.ForYouPresenter;
import com.example.thecoffeehouse.news.presenter.NewsPresenter;
import com.example.thecoffeehouse.news.viewnews.ForYouView;
import com.example.thecoffeehouse.news.viewnews.NewsView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment implements NewsView, ForYouView {
    View rootView;
    private NewsAdapter adapter;
    private NewsAdapterNews adapter2;
    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;
    private NewsPresenter newsPresenter;
    private ForYouPresenter forYouPresenter;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.for_you_recycleview);
        final RecyclerView recyclerViewNews = (RecyclerView) rootView.findViewById(R.id.news_recycleview);
        List<ResponseNews> listNews= new ArrayList<> ();
        List<ResponseForYou> listForYou = new ArrayList<>();
       adapter = new NewsAdapter (getContext(),listForYou,getFragmentManager());
        adapter2 = new NewsAdapterNews(getContext(),listNews, getFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //recyclerViewNews.setVerticalFadingEdgeEnabled(true);
        RecyclerView.LayoutManager mNewsLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerViewNews.setLayoutManager(mNewsLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapter2);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private  void getForYouList()
    {
        forYouPresenter.getForYou();
    }
    private void getNewsList() {

       newsPresenter.getNews ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupNews();
    }

    public void setupNews()
    {
        newsPresenter=new NewsPresenter(NewsFragment.this);
        forYouPresenter=new ForYouPresenter(NewsFragment.this);
    }

    private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
    }

    @Override
    public void displayNews(List<ResponseNews> itemList) {
        List<ResponseNews> list = new ArrayList<> ();
        if (itemList != null) {
            for (ResponseNews item : itemList) {
                    list.add (item);
                }
            }
        adapter2.setValues(list);
    }

    @Override
    public void displayForYou(List<ResponseForYou> for_you_itemlist) {
        List<ResponseForYou> listforyou = new ArrayList<> ();
        if (for_you_itemlist != null) {
            for (ResponseForYou item : for_you_itemlist) {
                listforyou.add (item);
            }
        }
        adapter.setValues(listforyou);
    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void onResume() {
        super.onResume ();
        getNewsList ();
        getForYouList();
    }
}
