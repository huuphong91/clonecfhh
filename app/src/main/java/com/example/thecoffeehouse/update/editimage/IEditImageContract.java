package com.example.thecoffeehouse.update.editimage;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.thecoffeehouse.data.model.bill.Bill;

import java.util.ArrayList;

public interface IEditImageContract {

    interface View {
        void onChangeSuccess(String messege);
        void onChangeFail(String messege);
        Context getContextt();
    }

    interface Presenter{
        void editImage(String numberPhone, String linkOldIamge, Bitmap imageBitmapNew);
    }
}
