package com.example.thecoffeehouse.update.editfirstname;

import android.content.Context;

public interface IEditFirstNameContract {

    interface View{
        void onEditSuccess(String messege);
        void onEditFail(String messege);
        Context getContextt();
    }

    interface Presenter{
        void editLastName(String numberPhone,String firstName);
    }
}
