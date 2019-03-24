package com.teamducati.cloneappcfh.screen.news;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.main.MainViewPager;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast_Factory;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsPromotionListAdapter;
import com.teamducati.cloneappcfh.screen.news.notification.NewsNotificationDialogFragment;
import com.teamducati.cloneappcfh.screen.news.notificationsdetails.NotificationDetailsDialogFragment;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class NewsFragment extends DaggerFragment implements NewsContract.View {

    @BindView(R.id.recycler_view_news_promotion)
    RecyclerView mRecyclerViewNewsPromotion;
    @BindView(R.id.recycler_view_news)
    RecyclerView mRecyclerViewNews;
    @BindView(R.id.img_news_notification_sign_out)
    ImageView imgNotificationSignOut;
    @BindView(R.id.img_news_notification_sign_in)
    ImageView imgNotificationSignIn;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.swipe_refresh_layout_news)
    SwipeRefreshLayout swipeRefreshLayoutLayout;
    private BottomNavigationView bottomNavigationView;
    @BindView(R.id.view_flipper)
    ViewFlipper mViewLayoutActionBar;
    @BindView(R.id.txt_name_news_login)
    TextView mTxtNameNewsLogin;
    @BindView(R.id.img_news_person)
    ImageView mImgNewsPerson;

    private UserBroadCast userBroadCast;

    @Inject
    NewsPromotionListAdapter mAdapterNewsPromotion;

    @Inject
    NewsListAdapter mAdapterNews;

    @Inject
    NewsContract.Presenter mPresenter;

    private MainViewPager mainViewPager;

    private User userObj;

    private Unbinder unbinder;

    @Inject
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        userBroadCast = UserBroadCast_Factory.newUserBroadCast(this);
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
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
        mRecyclerViewNewsPromotion.setHasFixedSize(true);
        mRecyclerViewNewsPromotion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void initRecyclerViewNews() {
        mRecyclerViewNews.setHasFixedSize(true);
        mRecyclerViewNews.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initEvent() {
        mainViewPager = new MainViewPager(getActivity(), null);
        btnLogin.setBackgroundResource(R.drawable.custom_button_selector);
        btnLogin.setOnClickListener(view ->
                bottomNavigationView.setSelectedItemId(R.id.navigation_account)
        );
        imgNotificationSignIn.setOnClickListener(view -> {
            NewsNotificationDialogFragment newsNotificationDialogFragment =
                    new NewsNotificationDialogFragment();
            newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);

        });
        imgNotificationSignOut.setOnClickListener(view -> {
            NewsNotificationDialogFragment newsNotificationDialogFragment =
                    new NewsNotificationDialogFragment();
            newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);

        });
        swipeRefreshLayoutLayout.setOnRefreshListener(() -> {
            mAdapterNews.notifyDataSetChanged();
            mAdapterNewsPromotion.notifyDataSetChanged();
            swipeRefreshLayoutLayout.setRefreshing(false);
        });
        mTxtNameNewsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
            }
        });
        mImgNewsPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
            }
        });
    }


    private void initActionBar() {
        userObj = new User();
        userObj = ActivityUtils.getDataObject(getActivity(), userObj.getClass());
        if (!(userObj == null)) {
            mTxtNameNewsLogin.setText(userObj.getFirstName());
            loadImage(userObj.getImgAvatarUrl(),mImgNewsPerson);
            mViewLayoutActionBar.setDisplayedChild(0);
        } else {
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
        mAdapterNewsPromotion.setList(arrayList);
        mRecyclerViewNewsPromotion.setAdapter(mAdapterNewsPromotion);
    }

    @Override
    public void getListNews(List<News> arrayList) {
        mAdapterNews.setList(arrayList);
        mRecyclerViewNews.setAdapter(mAdapterNews);
    }

    @Override
    public void getHandleSuccess() {
        swipeRefreshLayoutLayout.setOnRefreshListener(() -> swipeRefreshLayoutLayout.setRefreshing(false));
    }

    @Override
    public void getHandleError() {

    }

    @Override
    public void showUser(User user) {
        //broadcast
        if (user != null) {
            mTxtNameNewsLogin.setText(user.getFirstName());
            loadImage(user.getImgAvatarUrl(),mImgNewsPerson);
            mViewLayoutActionBar.setDisplayedChild(0);
           // ActivityUtils.setDataObject(getActivity(),user);
        } else {
            mViewLayoutActionBar.setDisplayedChild(1);
        }
    }
    public void loadImage(String url,ImageView imageView) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_USER_RESULT);
        intentFilter.addAction(Constants.ACTION_LOG_OUT);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .registerReceiver(userBroadCast, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(userBroadCast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
