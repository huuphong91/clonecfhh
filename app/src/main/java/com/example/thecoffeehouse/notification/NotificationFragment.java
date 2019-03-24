package com.example.thecoffeehouse.notification;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.update.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ImageView mImageViewBack;
    private NotificationAdapter adapter;
    private FragmentInteractionListener mListener;
    private OnUpdateListener onUpdateListener;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
        if (context instanceof OnUpdateListener) {
            onUpdateListener = (OnUpdateListener) context;
        }
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.frag_notification_recyclerview);
        mImageViewBack = view.findViewById(R.id.frag_notification_imgBack);
        adapter = new NotificationAdapter(getActivity());
//        presenter = new NotificationPresenter(this);
//        presenter.loadNotification().observe(this, new Observer<List<Notification>>() {
//            @Override
//            public void onChanged(List<Notification> notifications) {
//                adapter.setNotification(notifications);
//            }
//        });
        AppRespositoryImp appRespositoryImp = new AppRespositoryImp(getActivity().getApplication());
        appRespositoryImp.getNotification((LifecycleOwner) getContext()).observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                adapter.setNotification(notifications);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        mImageViewBack.setOnClickListener(v -> {
            onUpdateListener.onUpdateFragment();
        });
    }

}
