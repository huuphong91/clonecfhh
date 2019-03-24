package com.teamducati.cloneappcfh.screen.account;

import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

public interface AccountContract {

    interface Presenter extends BasePresenter<View> {

        void onLogout();
    }

    interface View extends BaseView<Presenter> {

        void showUserDetail(User user);

        void showLoginScreen();

        void restartViewAccount();

        void showLoginFail(String whyFail);

        void showUpdateUserPropertySuccess();

        void showUpdateUserPropertyFail();


    }
}

