package com.teamducati.cloneappcfh.screen.account;

import com.teamducati.cloneappcfh.entity.User;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class AccountPresenter implements AccountContract.Presenter {

    @Nullable
    private AccountContract.View mAccountView;

    private User userObj;

    @Inject
    public AccountPresenter() {
    }

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
