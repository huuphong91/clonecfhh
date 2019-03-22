package com.example.thecoffeehouse.update.editgender;

import android.content.Context;

public interface IEditGenderContract {

    interface View{
        void onChangedSuccess(String messege);
        Context getContexttt();
    }

    interface Presenter{
        void changeGender(String numberPhone, String gender);
    }
}
