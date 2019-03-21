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
        interface AccountView extends View {

            void showLoginView();

            void showProfileView();
        }

        interface LoginView extends View {
            void showLoginSuccess();

            void showLoginFailed(String error);

        }

        interface ProfileView extends View {

            void showProfileAccount();

            void restartViewAccount();

        }

        interface UpdateView extends View {

            void showUpdateSuccess();

            void showUpdateFailed(String error);
        }

    }
}

