package com.teamducati.cloneappcfh.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {

        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void chooseFragmentWannaDisplay(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();

    }
}
