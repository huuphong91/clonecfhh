package com.teamducati.cloneappcfh.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.teamducati.cloneappcfh.screen.main.MainActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mSharedPreferencesEditor;

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void chooseFragmentWannaDisplay(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void restartAllFragmentDisplay(Activity getActivity) {
        if (getActivity != null) {
            Intent intent = new Intent(getActivity, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity.startActivity(intent);
            getActivity.overridePendingTransition(0, 0); //0 for no animation
        }
    }

    public static void createDataObject(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

    }

    public static void setDataObject(Object objectData) {
        mSharedPreferencesEditor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String mSetStringJSONObject = gson.toJson(objectData);
//        mSharedPreferencesEditor.putString("data_object_" +
//                objectData.getClass().getSimpleName().toLowerCase(), mSetStringJSONObject);
        mSharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_KEY_DATA_OBJECT, mSetStringJSONObject);
        mSharedPreferencesEditor.apply();
    }

    public static <T> T getDataObject(Class<T> objectData) {
        Gson gson = new Gson();
//        String mGetStringJSONObject = mSharedPreferences.getString("data_object_" +
//                objectData.getClass().getSimpleName().toLowerCase(), "");
        String mGetStringJSONObject = mSharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY_DATA_OBJECT, null);
        return gson.fromJson(mGetStringJSONObject, objectData);
    }
//    public static void removeDataObject(Object objectData) {
//        mSharedPreferencesEditor = mSharedPreferences.edit();
////        mSharedPreferencesEditor.remove("data_object_" +
////                objectData.getClass().getSimpleName().toLowerCase());
//        mSharedPreferencesEditor.remove(Constants.SHARED_PREFERENCES_KEY_DATA_OBJECT);
//        mSharedPreferencesEditor.apply();
//    }

    public static void removeAllDataObject() {
        mSharedPreferencesEditor = mSharedPreferences.edit();
        mSharedPreferencesEditor.clear();
        mSharedPreferencesEditor.apply();

    }

}