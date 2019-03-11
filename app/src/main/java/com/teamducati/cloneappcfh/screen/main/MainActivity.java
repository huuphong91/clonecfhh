package com.teamducati.cloneappcfh.screen.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.MainFragmentsPagerAdapter;
import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.store.StoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.viewPager)
    MainViewPager mViewPager;

    private MainFragmentsPagerAdapter mFragmentsPagerAdapter;

    private NewsFragment mNewsFragment;
    private OrderFragment mOrderFragment;
    private StoreFragment mStoreFragment;
    private AccountFragment mAccountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        int quantityFragments = 4;
        //No need to recreate screen page anymore
        mViewPager.setOffscreenPageLimit(quantityFragments);
        //Set no swiping page in view pager
        mViewPager.setPagingEnabled(false);

        createPagerAdapterAndMainFragments();

        addFragmentToPagerAdapter();

        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setItemIconTintList(null);
    }

    private void createPagerAdapterAndMainFragments() {
        mFragmentsPagerAdapter = new MainFragmentsPagerAdapter(getSupportFragmentManager());
        mNewsFragment = new NewsFragment();
        mOrderFragment = new OrderFragment();
        mStoreFragment = new StoreFragment();
        mAccountFragment = new AccountFragment();
    }

    private void addFragmentToPagerAdapter() {
        mFragmentsPagerAdapter.addFragments(mNewsFragment);
        mFragmentsPagerAdapter.addFragments(mOrderFragment);
        mFragmentsPagerAdapter.addFragments(mStoreFragment);
        mFragmentsPagerAdapter.addFragments(mAccountFragment);
        mViewPager.setAdapter(mFragmentsPagerAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        int positionFragment;
        switch (item.getItemId()) {
            case R.id.navigation_news:
                positionFragment = 0;
                setCurrentItem(positionFragment);
                return true;
            case R.id.navigation_order:
                positionFragment = 1;
                setCurrentItem(positionFragment);
                return true;
            case R.id.navigation_store:
                positionFragment = 2;
                setCurrentItem(positionFragment);
                return true;
            case R.id.navigation_account:
                positionFragment = 3;
                setCurrentItem(positionFragment);
                return true;
        }
        return false;
    };

    private void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
    }
}
