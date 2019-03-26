package com.teamducati.cloneappcfh.screen.main;

import com.teamducati.cloneappcfh.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

@ActivityScoped
public class MainFragmentsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    @Inject
    MainFragmentsPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    void addFragments(Fragment fragment) {
        mFragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
