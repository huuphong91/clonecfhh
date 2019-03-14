package com.teamducati.cloneappcfh.screen.news;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.adapter.NewsPromotionListAdapter;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.screen.main.MainViewPager;
import com.teamducati.cloneappcfh.screen.news.notification.NewsNotificationActivity;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements NewsContract.View {
    private View view;

    private RecyclerView mRecyclerViewNewsPromotion;

    private RecyclerView mRecyclerViewNews;

    private NewsPromotionListAdapter mAdapterNewsPromotion;

    private NewsListAdapter mAdapterNews;

    private NewsContract.Presenter mPresenter;

    private MainViewPager mainViewPager;

    private ImageView imgNotification;

    private Button btnLogin;

    private SwipeRefreshLayout swipeRefreshLayoutLayout;

    private BottomNavigationView bottomNavigationView;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initMappingViewId();
        initEvent();
        initUI();
        return view;
    }

    private void initUI() {
        initRecyclerViewNews();
        initRecyclerViewNewsPromotion();
    }

    private void initEvent() {
        mainViewPager = new MainViewPager(getActivity(), null);
        btnLogin.setBackgroundResource(R.drawable.custom_button_selector);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
            }
        });
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsNotificationActivity.class);
                //fragment not do use clear top
                startActivity(intent);
            }
        });
        swipeRefreshLayoutLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapterNews.notifyDataSetChanged();
                mAdapterNewsPromotion.notifyDataSetChanged();
                swipeRefreshLayoutLayout.setRefreshing(false);
            }
        });
    }

    public void initRecyclerViewNewsPromotion() {
        mRecyclerViewNewsPromotion = (RecyclerView) view.findViewById(R.id.recycler_view_news_promotion);
        mRecyclerViewNewsPromotion.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNewsPromotion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //  mRecyclerView.setLayoutManager(layoutManager);
    }

    public void initRecyclerViewNews() {
        mRecyclerViewNews = (RecyclerView) view.findViewById(R.id.recycler_view_news);
        mRecyclerViewNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNews.setLayoutManager(layoutManager);
    }

    @Override
    public void getListNewsPromotion(List<NewsPromotion> arrayList) {
        mAdapterNewsPromotion = new NewsPromotionListAdapter(getActivity(), arrayList);
        mRecyclerViewNewsPromotion.setAdapter(mAdapterNewsPromotion);
    }

    @Override
    public void getListNews(List<News> arrayList) {
        mAdapterNews = new NewsListAdapter(getActivity(), arrayList);
        mRecyclerViewNews.setAdapter(mAdapterNews);
    }

    private void initMappingViewId() {

        mRecyclerViewNewsPromotion = view.findViewById(R.id.recycler_view_news_promotion);
        mRecyclerViewNews = view.findViewById(R.id.recycler_view_news);
        imgNotification = view.findViewById(R.id.img_news_notification);
        btnLogin = view.findViewById(R.id.btn_login);
        swipeRefreshLayoutLayout = view.findViewById(R.id.swipe_refresh_layout_news);
        //bottomNavigation
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getHandleSuccess() {
        swipeRefreshLayoutLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void getHandleError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
