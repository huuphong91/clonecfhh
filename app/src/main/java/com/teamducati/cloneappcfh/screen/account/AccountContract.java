package com.teamducati.cloneappcfh.screen.account;

import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

public interface AccountContract {

    interface Presenter extends BasePresenter {
        void onCheckDataAccount();

        void onLoginAccount(User user);

        void onLogoutAccount();

        void onGetProfile();

        void onUpdateAccount(User user);

    }

    interface View extends BaseView<AccountContract.Presenter> {
        void showLoginView();

        void showProfileView();

        void showDialogView();

        void showLoginSuccess();

        void showLoginFailed(String error);

        void showProfileSuccess();

        void showProfileFailed(String error);

        void showUpdateSuccess();

        void showUpdateFailed(String error);

        void restartViewAccount();
    }
}

