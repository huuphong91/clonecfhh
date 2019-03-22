package com.example.thecoffeehouse.update.editimage;

import android.content.Context;

public interface IEditImageContract {

    interface View {
        void onChangeSuccess(String messege);
        Context getContextt();
    }

    interface Presenter{
        void changeImage(String numberPhone, String imageBitmap);
    }
}
