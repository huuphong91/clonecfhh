package com.teamducati.cloneappcfh.screen.news;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.main.MainViewPager;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsPromotionListAdapter;
import com.teamducati.cloneappcfh.screen.news.notification.NewsNotificationDialogFragment;
import com.teamducati.cloneappcfh.screen.news.notificationsdetails.NotificationDetailsDialogFragment;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

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

    private ImageView imgNotificationSignOut;

    private ImageView imgNotificationSignIn;

    private Button btnLogin;

    private SwipeRefreshLayout swipeRefreshLayoutLayout;

    private BottomNavigationView bottomNavigationView;

    private ViewFlipper mViewLayoutActionBar;

    private User userObj;
    private TextView mTxtNameNewsLogin;
    private ImageView mImgNewsPerson;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initMappingViewId();
        initEvent();
        initShowStartupDialogNotification();
        initUI();
        return view;
    }

    private void initUI() {
        initActionBar();
        initRecyclerViewNews();
        initRecyclerViewNewsPromotion();
    }

    public void initRecyclerViewNewsPromotion() {
        mRecyclerViewNewsPromotion = (RecyclerView) view.findViewById(R.id.recycler_view_news_promotion);
        mRecyclerViewNewsPromotion.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNewsPromotion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void initRecyclerViewNews() {
        mRecyclerViewNews = (RecyclerView) view.findViewById(R.id.recycler_view_news);
        mRecyclerViewNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNews.setLayoutManager(layoutManager);
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
        imgNotificationSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsNotificationDialogFragment newsNotificationDialogFragment =
                        new NewsNotificationDialogFragment();
                newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);

            }
        });
        imgNotificationSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsNotificationDialogFragment newsNotificationDialogFragment =
                        new NewsNotificationDialogFragment();
                newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);

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

    private void initActionBar() {
        //ActivityUtils.removeAllDataObject(getActivity());
        userObj = new User();
        userObj = ActivityUtils.getDataObject(getActivity(), userObj.getClass());
        if (!(userObj == null)) {
            Log.d("Data", userObj.toString());
            mTxtNameNewsLogin.setText(userObj.getFirstName());
            Glide.with(getContext())
                    .load(userObj.getImgAvatarUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImgNewsPerson);
            mViewLayoutActionBar.setDisplayedChild(0);
        } else {
            Log.d("Data", "null data");
            mViewLayoutActionBar.setDisplayedChild(1);
        }
    }

    private void initShowStartupDialogNotification() {
        Intent intent = getActivity().getIntent();
        if (intent.getStringExtra("firebase_id") != null) {
            NotificationDetailsDialogFragment newsNotificationDialogFragment =
                    new NotificationDetailsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title_notification", intent.getStringExtra("firebase_title"));
            bundle.putString("content_notification", intent.getStringExtra("firebase_content"));
            bundle.putString("image_notification", intent.getStringExtra("firebase_url"));
            newsNotificationDialogFragment.setArguments(bundle);
            newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);
        } else {

        }
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
        imgNotificationSignIn = view.findViewById(R.id.img_news_notification_sign_in);
        imgNotificationSignOut = view.findViewById(R.id.img_news_notification_sign_out);
        btnLogin = view.findViewById(R.id.btn_login);
        swipeRefreshLayoutLayout = view.findViewById(R.id.swipe_refresh_layout_news);
        //bottomNavigation
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        mViewLayoutActionBar = view.findViewById(R.id.view_flipper);
        mTxtNameNewsLogin = view.findViewById(R.id.txt_name_news_login);
        mImgNewsPerson = view.findViewById(R.id.img_news_person);
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
