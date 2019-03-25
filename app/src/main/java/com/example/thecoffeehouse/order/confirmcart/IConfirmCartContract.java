package com.example.thecoffeehouse.order.confirmcart;

import android.content.Context;

public interface IConfirmCartContract {
    interface View{
        void onConfirmSucess(String result);
        void onConfirmFaild(String result);
        Context getContext();
    }

    interface Presenter{
        void confirmCart(String numberPhone, int totalPrice, int totalCup, int timeCount);
    }
}
