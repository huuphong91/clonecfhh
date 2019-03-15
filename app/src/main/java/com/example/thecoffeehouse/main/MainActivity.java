package com.example.thecoffeehouse.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.order.OrderFragment;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.views.StoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager mFragmentManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    loadFragment(NewsFragment.newInstance());
                    return true;
                case R.id.navigation_order:
                    loadFragment(OrderFragment.newInstance());
                    return true;
                case R.id.navigation_store:
                    loadFragment(StoreFragment.newInstance());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(ProfileFragment.newInstance());
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
        mFragmentManager = getSupportFragmentManager();
    }

    private void loadFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
