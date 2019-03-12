package com.teamducati.cloneappcfh.screen.account;

import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.screen.news.NewsContract;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

public interface AccountContract {

    interface Presenter extends BasePresenter {

        void onLogin(User user);

        void onLogout(User user);

        void updateUserProperty(User user);
    }

    interface View extends BaseView<AccountContract.Presenter> {

        void showUserDetail(User user);

        void showLoginFail(String whyFail);

        void showUpdateUserPropertySuccess();

        void showUpdateUserPropertyFail();

        void showLoginScreen();
    }
}

