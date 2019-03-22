package com.example.thecoffeehouse.main;

import android.util.Log;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentBackstackChangeListenerImpl implements FragmentManager.OnBackStackChangedListener {

    private int lastBackStackEntryCount = 0;
    private final FragmentManager fragmentManager;
    private final BackstackCallback backstackChangeListener;

    public FragmentBackstackChangeListenerImpl(FragmentManager fragmentManager, BackstackCallback backstackChangeListener) {
        this.fragmentManager = fragmentManager;
        this.backstackChangeListener = backstackChangeListener;
        lastBackStackEntryCount = fragmentManager.getBackStackEntryCount();
    }

    private boolean wasPushed(int backStackEntryCount) {
        return lastBackStackEntryCount < backStackEntryCount;
    }

    private boolean wasPopped(int backStackEntryCount) {
        return lastBackStackEntryCount > backStackEntryCount;
    }

    private boolean haveFragments() {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        return fragmentList != null && !fragmentList.isEmpty();
    }

    private Fragment getParentFragment() {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        return fragmentList.get(Math.max(0, fragmentList.size() - 2));
    }

    @Override
    public void onBackStackChanged() {
        int currentBackStackEntryCount = fragmentManager.getBackStackEntryCount();
        Log.d("onBackStackChanged: ", currentBackStackEntryCount + " ---");
        if (haveFragments()) {
            Fragment parentFragment = getParentFragment();

            if (parentFragment != null) {
                if (wasPushed(currentBackStackEntryCount)) {
                    backstackChangeListener.onFragmentPushed(parentFragment);
                } else if (wasPopped(currentBackStackEntryCount)) {
                    backstackChangeListener.onFragmentPopped(parentFragment);
                }
            }
        }
        lastBackStackEntryCount = currentBackStackEntryCount;
    }
}
