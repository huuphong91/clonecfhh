package com.example.thecoffeehouse.update.editlastname;

import android.content.Context;

public interface IEditLastNameContract {

    interface View{
        void onEditSuccess(String messege);
        void onEditFail(String messege);
        Context getContextt();
    }

    interface Presenter{
        void editLastName(String numberPhone,String lastName);
    }

}
