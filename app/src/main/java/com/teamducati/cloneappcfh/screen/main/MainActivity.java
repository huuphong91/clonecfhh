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
import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.screen.news.NewsPresenter;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.order.OrderPresenter;
import com.teamducati.cloneappcfh.screen.store.StoreFragment;
import com.teamducati.cloneappcfh.screen.store.StorePresenter;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.viewPager)
    MainViewPager mViewPager;

    @Inject
    NewsPresenter mNewsPresenter;
    @Inject
    OrderPresenter mOrderPresenter;
    @Inject
    StorePresenter mStorePresenter;
    @Inject
    AccountPresenter mAccountPresenter;
    @Inject
    NewsFragment mNewsFragment;
    @Inject
    OrderFragment mOrderFragment;
    @Inject
    StoreFragment mStoreFragment;
    @Inject
    AccountFragment mAccountFragment;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private boolean isFirstClickOnOrderTab;
    private boolean isFirstClickOnStoreTab;

    private MainFragmentsPagerAdapter mFragmentsPagerAdapter;

    private FusedLocationProviderClient mFusedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isFirstClickOnOrderTab = true;
        isFirstClickOnStoreTab = true;

        mFragmentsPagerAdapter = MainFragmentsPagerAdapter_Factory.newMainFragmentsPagerAdapter(getSupportFragmentManager());

        checkApiKeyOfPlaces();

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        initData();

        initUI();
    }

    private void checkApiKeyOfPlaces() {
        String apiKey = getString(R.string.places_api_key);

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
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

        addFragmentToPagerAdapter();

        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setItemIconTintList(null);
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
