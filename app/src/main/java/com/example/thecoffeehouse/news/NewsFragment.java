package com.example.thecoffeehouse.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.news.adapter.NewsAdapterForYou;
import com.example.thecoffeehouse.news.adapter.NewsAdapterNews;
import com.example.thecoffeehouse.news.presenter.ForYouPresenterImpl;
import com.example.thecoffeehouse.news.presenter.NewsPresenterImpl;
import com.example.thecoffeehouse.news.viewnews.ForYouView;
import com.example.thecoffeehouse.news.viewnews.NewsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsFragment extends Fragment implements NewsView, ForYouView {

    View rootView;
    private TextView mTxtName, mTxtLogin;
    private ImageView mImgAccount, mImgNotification;
    private DatabaseReference mDataRef;
    private User mUser;
    private NewsAdapterForYou adapter;
    private NewsAdapterNews adapter2;
    private NewsPresenterImpl newsPresenter;
    private ForYouPresenterImpl forYouPresenter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewNews;

    private AppCompatActivity activity;
    List<ResponseForYou> listForYou;
    List<ResponseNews> listNews;
   private SwipeRefreshLayout swipeRefreshLayout;
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.for_you_recycleview);
        recyclerViewNews = (RecyclerView) rootView.findViewById(R.id.news_recycleview);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiper_fresh);
        listNews = new ArrayList<>();
        listForYou = new ArrayList<>();


        adapter = new NewsAdapterForYou(getContext(), listForYou, getFragmentManager());
        adapter2 = new NewsAdapterNews(getContext(), listNews, getFragmentManager());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mNewsLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewNews.setLayoutManager(mNewsLayoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNews.setAdapter(adapter2);
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

                RecyclerView.LayoutManager mNewsLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerViewNews.setLayoutManager(mNewsLayoutManager);
                recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
                recyclerViewNews.setAdapter(adapter2);
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                swipeRefreshLayout.getDrawingTime();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return rootView;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getForYouList() {
        forYouPresenter.getForYou();
        forYouPresenter.loadListNewsFromDatabase();

    }
    private  void getNewsList()
    {
        newsPresenter.getNews();
        newsPresenter.loadNewsForFromDatabase();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupNews();
    }

    public void setupNews() {
        newsPresenter = new NewsPresenterImpl(NewsFragment.this);
        forYouPresenter = new ForYouPresenterImpl(NewsFragment.this);
    }

        private void initView(View view) {
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
        mTxtName = view.findViewById(R.id.news_account_name);
        mTxtName.setVisibility(View.GONE);
        mTxtLogin = view.findViewById(R.id.news_text_account);
        mImgAccount = view.findViewById(R.id.news_account_image);
        mImgNotification = view.findViewById(R.id.news_push_notification);
        mDataRef = FirebaseDatabase.getInstance().getReference("Users");
        activity = (AppCompatActivity) getActivity();
        mUser = new User();

    }

    @Override
    public void displayNews(List<ResponseNews> itemList) {
        List<ResponseNews> list = new ArrayList<>();
        if (itemList != null) {
            for (ResponseNews item : itemList) {
                list.add(item);
            }
        }
        adapter2.setValues(list);
    }


    @Override
    public void displayForYou(List<ResponseForYou> itemList) {
        List<ResponseForYou> list = new ArrayList<>();
        if (itemList != null) {
            for (ResponseForYou item : itemList) {
                list.add(item);
            }
        }
        adapter.setValues(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNewsList();
        getForYouList();
    }

}
