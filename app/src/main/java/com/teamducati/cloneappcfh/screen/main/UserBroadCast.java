package com.teamducati.cloneappcfh.screen.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.utils.BaseView;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.Objects;

import javax.inject.Inject;

public class UserBroadCast extends BroadcastReceiver {

    private BaseView baseView;

    @Inject
    public UserBroadCast(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (Objects.requireNonNull(action)) {
            case Constants.ACTION_USER_RESULT:
                if (baseView instanceof AccountFragment) {
                    ((AccountFragment)baseView).showUserDetail(intent.getParcelableExtra("User"));
                }
                if (baseView instanceof NewsFragment) {
                    ((NewsFragment) baseView).showUser(intent.getParcelableExtra("User"));
                }
                break;
            case Constants.ACTION_LOG_OUT:
                if (baseView instanceof AccountFragment) {
                    ((AccountFragment)baseView).showLoginScreen();
                }
                if (baseView instanceof NewsFragment) {
                    ((NewsFragment) baseView).showUser(intent.getParcelableExtra("User"));
                }
                break;
        }
    }
}
