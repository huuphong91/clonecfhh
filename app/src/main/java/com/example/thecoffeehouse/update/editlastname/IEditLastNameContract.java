package com.example.thecoffeehouse.update.editlastname;

import android.content.Context;

import com.example.thecoffeehouse.data.model.bill.Bill;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
