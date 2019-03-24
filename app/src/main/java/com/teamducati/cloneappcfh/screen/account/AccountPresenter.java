package com.teamducati.cloneappcfh.screen.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.eventsbus.EventBusStore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountPresenter implements AccountContract.Presenter {

    @Nullable
    private AccountContract.View mAccountView;

    private User userObj;

    @Inject
    public AccountPresenter() {
    }

//    @Override
//    public void start() {
//        userObj = ActivityUtils.getDataObject(context, userObj.getClass());
//        if (!(userObj==null)) {
//            mAccountView.showUserDetail();
//        } else {
//            mAccountView.showLoginScreen();
//        }
//    }

    @Override
    public void onLogout() {
//        ActivityUtils.removeAllDataObject(context);
        if (mAccountView != null) {
            mAccountView.restartViewAccount();
        }

    }

    @Override
    public void takeView(AccountContract.View view) {
        mAccountView = view;
    }

    @Override
    public void dropView() {
        mAccountView = null;
    }
}
