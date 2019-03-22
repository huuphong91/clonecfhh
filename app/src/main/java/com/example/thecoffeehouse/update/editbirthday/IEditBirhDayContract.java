package com.example.thecoffeehouse.update.editbirthday;

import android.content.Context;

public interface IEditBirhDayContract {
    interface View{
        void onChangeBirthdaySuccess(String messege);
        void onChangeBirthdayFail(String messege);
        Context getContextt();
    }

    interface Presenter{
        void changeBirthDay(String numberPhone, String birthDay);
    }
}
