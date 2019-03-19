package com.teamducati.cloneappcfh.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    private static SharedPreferences sharedPreferences;
    private static Gson gson;

    public static String formatMoney(double money) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(money).replace(',', '.') + " đ";
    }

    public static SharedPreferences getSharedPreferencesInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_CART_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
