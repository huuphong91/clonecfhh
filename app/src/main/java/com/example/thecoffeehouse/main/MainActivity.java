package com.example.thecoffeehouse.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.adapter.ViewPagerAdapter;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.order.OrderFragment;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.StoreFragment;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private ViewPagerAdapter mViewPagerAdapter;
    private MenuItem prevMenuItem;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_order:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_store:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_profile:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager = findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter.addFragment(NewsFragment.newInstance(), Constant.NEWS_FRAGMENT);
        mViewPagerAdapter.addFragment(OrderFragment.newInstance(), Constant.ORDER_FRAGMENT);
        mViewPagerAdapter.addFragment(StoreFragment.newInstance(), Constant.STORE_FRAGMENT);
        mViewPagerAdapter.addFragment(ProfileFragment.newInstance(), Constant.PROFILE_FRAGMENT);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            navigation.getMenu().getItem(0).setChecked(false);
        }
        navigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = navigation.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
