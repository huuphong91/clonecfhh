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
import com.google.android.material.snackbar.Snackbar;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.di.ActivityScoped;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast;
import com.teamducati.cloneappcfh.screen.main.UserBroadCast_Factory;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsListAdapter;
import com.teamducati.cloneappcfh.screen.news.adapter.NewsPromotionListAdapter;
import com.teamducati.cloneappcfh.screen.news.notification.NewsNotificationDialogFragment;
import com.teamducati.cloneappcfh.screen.news.notificationsdetails.NotificationDetailsDialogFragment;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
@SuppressWarnings("unchecked")
public class NewsFragment extends DaggerFragment implements NewsContract.View, View.OnClickListener {

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
    @BindView(R.id.view_flipper)
    ViewFlipper mViewLayoutActionBar;
    @BindView(R.id.txt_name_news_login)
    TextView mTxtNameNewsLogin;
    @BindView(R.id.img_news_person)
    ImageView mImgNewsPerson;
    @Inject
    NewsPromotionListAdapter mAdapterNewsPromotion;
    @Inject
    NewsListAdapter mAdapterNews;
    @Inject
    NewsContract.Presenter mPresenter;
    private BottomNavigationView bottomNavigationView;
    private View view;
    private UserBroadCast userBroadCast;

    private Unbinder unbinder;

    @Inject
    public NewsFragment() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(User event) {
        //check data change
        initActionBar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        initBroadCastCheckLogin();
        initMappingViewId();
        initEvent();
        initUI();
        return view;
    }

    private void initUI() {
        initShowStartupDialogNotification();
        initActionBar();
        initRecyclerViewNews();
        initRecyclerViewNewsPromotion();
    }

    private void initRecyclerViewNewsPromotion() {
        mRecyclerViewNewsPromotion.setHasFixedSize(true);
        mRecyclerViewNewsPromotion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initRecyclerViewNews() {
        mRecyclerViewNews.setHasFixedSize(true);
        mRecyclerViewNews.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initEvent() {
        btnLogin.setOnClickListener(this);
        imgNotificationSignIn.setOnClickListener(this);
        imgNotificationSignOut.setOnClickListener(this);
        mTxtNameNewsLogin.setOnClickListener(this);
        mImgNewsPerson.setOnClickListener(this);
        swipeRefreshLayoutLayout.setOnRefreshListener(() -> mPresenter.takeView(this));
    }

    private void initBroadCastCheckLogin() {
        userBroadCast = UserBroadCast_Factory.newUserBroadCast(this);
    }

    private void initActionBar() {
        User userObj = new User();
        userObj = ActivityUtils.getDataObject(getActivity(), userObj.getClass());
        if (!(userObj == null)) {
            mTxtNameNewsLogin.setText(userObj.getFirstName());
            loadImage(userObj.getImgAvatarUrl(), mImgNewsPerson);
            mViewLayoutActionBar.setDisplayedChild(0);
        } else {
            mViewLayoutActionBar.setDisplayedChild(1);
        }
    }

    private void initShowStartupDialogNotification() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        if (intent.getStringExtra(Constants.FIREBASE_ID) != null) {
            NotificationDetailsDialogFragment newsNotificationDialogFragment =
                    new NotificationDetailsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_BUNDLE_FIREBASE_TITLE, intent.getStringExtra(Constants.FIREBASE_TITLE));
            bundle.putString(Constants.KEY_BUNDLE_FIREBASE_CONTENT, intent.getStringExtra(Constants.FIREBASE_CONTENT));
            bundle.putString(Constants.KEY_BUNDLE_FIREBASE_IMAGE_URL, intent.getStringExtra(Constants.FIREBASE_IMAGE_URL));
            newsNotificationDialogFragment.setArguments(bundle);
            newsNotificationDialogFragment.show(getActivity().getSupportFragmentManager(), null);
        }
    }

    private void initMappingViewId() {
        bottomNavigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.navigation);
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
        swipeRefreshLayoutLayout.setRefreshing(false);
    }

    @Override
    public void getHandleError() {
        swipeRefreshLayoutLayout.setRefreshing(false);
        Snackbar snackbar = Snackbar.make(view, "Load Data Failed", Snackbar.LENGTH_LONG);
        snackbar.setAction("Try Again", v -> {
            mPresenter.takeView(this);
            snackbar.dismiss();
        });
    }

    @Override
    public void showUser(User user) {
        //broadcast
        if (user != null) {
            mTxtNameNewsLogin.setText(user.getFirstName());
            loadImage(user.getImgAvatarUrl(), mImgNewsPerson);
            mViewLayoutActionBar.setDisplayedChild(0);
            // ActivityUtils.setDataObject(getActivity(),user);
        } else {
            mViewLayoutActionBar.setDisplayedChild(1);
        }
    }

    private void loadImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView);
    }

    private void onBroadCastCheckLogin() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_USER_RESULT);
        intentFilter.addAction(Constants.ACTION_LOG_OUT);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .registerReceiver(userBroadCast, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
                break;
            case R.id.img_news_notification_sign_in:
                NewsNotificationDialogFragment newsNotificationDialogFragment = new NewsNotificationDialogFragment();
                newsNotificationDialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
                break;
            case R.id.img_news_notification_sign_out:
                newsNotificationDialogFragment = new NewsNotificationDialogFragment();
                newsNotificationDialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
                break;
            case R.id.txt_name_news_login:
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
                break;
            case R.id.img_news_person:
                bottomNavigationView.setSelectedItemId(R.id.navigation_account);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        onBroadCastCheckLogin();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
        EventBus.getDefault().unregister(this);
        mPresenter.dropView();
    }


}
