package com.teamducati.cloneappcfh.screen.news;


import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.adapter.NewsPromotionListAdapter;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewsContract.View {
    private RecyclerView mRecyclerViewNewsPromotion;

    private RecyclerView mRecyclerViewNews;

    private NewsPromotionListAdapter mAdapterNewsPromotion;

    private NewsListAdapter mAdapterNews;


    private NewsContract.Presenter mPresenter;

    private List<NewsPromotion> mNewsPromotions;

    private List<News> mNews;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initEvent();
        initRecyclerViewNewsPromotion(view);
        initRecyclerViewNews(view);
        return view;

    }


    private void initEvent() {
        mPresenter.getAllListNewsPromotion();
        mPresenter.getAllListNews();
    }

    private void initRecyclerViewNewsPromotion(View view) {
        mRecyclerViewNewsPromotion = (RecyclerView) view.findViewById(R.id.recycler_view_news_promotion);
        mRecyclerViewNewsPromotion.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNewsPromotion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //  mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initRecyclerViewNews(View view) {
        mRecyclerViewNews = (RecyclerView) view.findViewById(R.id.recycler_view_news);
        mRecyclerViewNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNews.setLayoutManager(layoutManager);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListNewsPromotion(List<NewsPromotion> arrayList) {
        this.mNewsPromotions = arrayList;
        mAdapterNewsPromotion = new NewsPromotionListAdapter(getActivity(), arrayList);
        mRecyclerViewNewsPromotion.setAdapter(mAdapterNewsPromotion);
    }

    @Override
    public void showListNews(List<News> arrayList) {
        this.mNews = arrayList;
        mAdapterNews = new NewsListAdapter (getActivity(), arrayList);
        mRecyclerViewNews.setAdapter(mAdapterNews);
    }

    @Override
    public void handleSuccess() {

    }

    @Override
    public void handleError() {

    }
}
