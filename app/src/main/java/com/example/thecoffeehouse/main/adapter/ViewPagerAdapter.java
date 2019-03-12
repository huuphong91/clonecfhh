package com.example.thecoffeehouse.main.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mListFragment;
    private ArrayList<String> mListTitle;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mListFragment = new ArrayList<>();
        mListTitle = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {
        mListFragment.add(fragment);
        mListTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle.get(position);
    }
}
