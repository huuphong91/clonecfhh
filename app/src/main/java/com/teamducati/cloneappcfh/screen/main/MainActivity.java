package com.teamducati.cloneappcfh.screen.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.Address;
import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.screen.news.NewsPresenter;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.order.OrderPresenter;
import com.teamducati.cloneappcfh.screen.store.StoreFragment;
import com.teamducati.cloneappcfh.screen.store.StorePresenter;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.viewPager)
    MainViewPager mViewPager;

    private FusedLocationProviderClient mFusedLocation;

    private NewsPresenter mNewsPresenter;
    private OrderPresenter mOrderPresenter;
    private StorePresenter mStorePresenter;
    private AccountPresenter mAccountPresenter;

    private MainFragmentsPagerAdapter mFragmentsPagerAdapter;

    private NewsFragment mNewsFragment;
    private OrderFragment mOrderFragment;
    private StoreFragment mStoreFragment;
    private AccountFragment mAccountFragment;

    private boolean isFirstClickOnOrderTab;
    private boolean isFirstClickOnStoreTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isFirstClickOnOrderTab = true;
        isFirstClickOnStoreTab = true;

        String apiKey = getString(R.string.places_api_key);

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        mFusedLocation = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(this));
        initData();
        initUI();
    }

    private void initData() {
        ActivityUtils.createDataObject(this);
    }

    private void initUI() {
        int quantityFragments = 4;
        //No need to recreate screen page anymore
        mViewPager.setOffscreenPageLimit(quantityFragments);
        //Set no swiping page in view pager
        mViewPager.setPagingEnabled(false);

        createPagerAdapterAndMainFragments();

        createPresenters();

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

    private void createPresenters() {
        mNewsPresenter = new NewsPresenter(mNewsFragment);
        mOrderPresenter = new OrderPresenter(mOrderFragment);
        mStorePresenter = new StorePresenter(mStoreFragment);
        mAccountPresenter = new AccountPresenter(this, mAccountFragment);
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
                if (isFirstClickOnOrderTab) {
                    getLocation(R.id.navigation_order);
                    isFirstClickOnOrderTab = false;
                }

                return true;
            case R.id.navigation_store:
                positionFragment = 2;
                setCurrentItem(positionFragment);
                if (isFirstClickOnStoreTab) {
                    getLocation(R.id.navigation_store);
                    isFirstClickOnStoreTab = false;
                }
                return true;
            case R.id.navigation_account:
                positionFragment = 3;
                setCurrentItem(positionFragment);
                return true;
        }
        return false;
    };

    public void getLocation(int itemId) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocation.getLastLocation().addOnSuccessListener(location -> {
                if (location != null && itemId == R.id.navigation_order) {
                    // mStoreFragment.setLocation(location);
                    new FetchAddressTask(this).execute(location);
                }
            });
        }
    }

    private void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation(R.id.navigation_order);
                } else {
                    Toast.makeText(this,
                            "Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
