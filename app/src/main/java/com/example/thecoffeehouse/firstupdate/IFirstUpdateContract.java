package com.example.thecoffeehouse.firstupdate;

import android.content.Context;

public interface IFirstUpdateContract {

    interface View {
        void insertUserSuccess(String messege);
        void insertUserFail(String messege);
        Context activity();
    }

    interface Presenter{
        void insertUser(String numberPhone, String firstName, String lastName);
    }
}
