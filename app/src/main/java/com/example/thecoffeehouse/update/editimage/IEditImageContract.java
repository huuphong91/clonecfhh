package com.example.thecoffeehouse.update.editimage;

import android.content.Context;
import android.graphics.Bitmap;

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
