package com.example.thecoffeehouse.login;

import android.content.Context;

public interface IPresenterLoginContract {
    interface View{
        void onCheckSucess(String result);
        void onCheckFail(String result);
        void onLoadSucess(String result);
        void onFirstLogin(String result);
        void onCancel(String result);

        Context activity();
    }

    interface Presenter{
        void checkFirstLogin(String numberPhone);
        void loadUser(String numberPhone);
    }
}
