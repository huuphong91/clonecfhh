package com.teamducati.cloneappcfh.screen.news.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.news.adapter.NotificationListAdapter;
import com.teamducati.cloneappcfh.entity.Notification;
import com.teamducati.cloneappcfh.screen.main.MainActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsNotificationActivity extends AppCompatActivity implements NoticationContract.View {

    private NoticationContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private NotificationListAdapter mNotificationListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imgCloseNotificaton;
    private View mViewLayoutSignIn;
    private View mViewLayoutSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_notification);
        initMappingViewId();
        initEvent();
        initUI();
    }

    private void initUI() {
        initRecyclerViewNotification();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNotificationListAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        imgCloseNotificaton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerViewNotification() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void getListNotification(List<Notification> arrayList) {
        mNotificationListAdapter = new NotificationListAdapter(getApplicationContext(), arrayList);
        mRecyclerView.setAdapter(mNotificationListAdapter);
    }

    @Override
    public void setPresenter(NoticationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initMappingViewId() {
        mRecyclerView = findViewById(R.id.recycler_view_news_notification);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_notification);
        imgCloseNotificaton = findViewById(R.id.img_close_notificaton);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter = new NotificationPresenter(this, getApplication(), this);
        mPresenter.start();
    }
}

