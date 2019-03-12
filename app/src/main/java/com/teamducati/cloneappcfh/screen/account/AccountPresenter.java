package com.teamducati.cloneappcfh.screen.account;

import com.teamducati.cloneappcfh.entity.User;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mAccountView;

    public AccountPresenter(AccountContract.View accountView) {
        this.mAccountView = accountView;
        mAccountView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onLogin(User user) {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void updateUserProperty(User user) {

    }
}
