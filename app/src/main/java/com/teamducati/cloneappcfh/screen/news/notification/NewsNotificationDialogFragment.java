package com.teamducati.cloneappcfh.screen.news.notification;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.news.adapter.NotificationListAdapter;
import com.teamducati.cloneappcfh.entity.Notification;

import java.util.List;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsNotificationDialogFragment extends DialogFragment implements NoticationContract.View {

    private NoticationContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private NotificationListAdapter mNotificationListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imgCloseNotificaton;
    private View view;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_notification, container, false);
        initMappingViewId();
        initEvent();
        initUI();
        return view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
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
                dialog.dismiss();
            }
        });
    }

    private void initRecyclerViewNotification() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void getListNotification(List<Notification> arrayList) {
        mNotificationListAdapter = new NotificationListAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mNotificationListAdapter);
    }

    private void initMappingViewId() {
        mRecyclerView = view.findViewById(R.id.recycler_view_news_notification);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_notification);
        imgCloseNotificaton = view.findViewById(R.id.img_close_notificaton);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mPresenter = new NotificationPresenter(this, getActivity(), this);
        mPresenter.onAllListNotification();

    }

}

