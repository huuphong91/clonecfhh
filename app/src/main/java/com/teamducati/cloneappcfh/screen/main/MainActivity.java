package com.teamducati.cloneappcfh.screen.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.adapter.MainFragmentsPagerAdapter;
import com.teamducati.cloneappcfh.screen.account.AccountFragment;
import com.teamducati.cloneappcfh.screen.account.AccountPresenter;
import com.teamducati.cloneappcfh.screen.news.NewsFragment;
import com.teamducati.cloneappcfh.screen.news.NewsPresenter;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.order.OrderPresenter;
import com.teamducati.cloneappcfh.screen.store.StoreFragment;
import com.teamducati.cloneappcfh.screen.store.StorePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.viewPager)
    MainViewPager mViewPager;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient mFusedLocation;
    private Location mCurrentLocation;

    private NewsPresenter mNewsPresenter;
    private OrderPresenter mOrderPresenter;
    private StorePresenter mStorePresenter;
    private AccountPresenter mAccountPresenter;

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

        mFusedLocation = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(this));

        initUI();
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
        mAccountPresenter = new AccountPresenter(mAccountFragment);
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
                getLocation();
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocation.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    new FetchAddressTask().execute(location);
                }
            });
        }
        mOrderFragment.setLocation("Loading...");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            "Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class FetchAddressTask extends AsyncTask<Location, Void, String> {

        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            Location location = locations[0];
            List<Address> addresses = null;
            String resultMessage = "";
            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        // In this sample, get just a single address
                        1);
                if (addresses == null || addresses.size() == 0) {
                    if (resultMessage.isEmpty()) {
                        resultMessage = "No address Found";
                        Log.e("TAG", resultMessage);
                    }
                } else {
                    // If an address is found, read it into resultMessage
                    Address address = Objects.requireNonNull(addresses).get(0);
                    ArrayList<String> addressParts = new ArrayList<>();

                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressParts.add(address.getAddressLine(i));
                    }
                    Log.d("addressParts", addressParts.get(0));

                    resultMessage = address.getSubThoroughfare()+ address.getThoroughfare();
                    Log.d("resultMessageFace", resultMessage);
                }
            } catch (IOException ioException) {
                // Catch network or other I/O problems
                resultMessage = "Not available";
                Log.e("TAG", resultMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values
                resultMessage = "invalid_lat_long_used";
                Log.e("TAG", resultMessage + ". " +
                        "Latitude = " + location.getLatitude() +
                        ", Longitude = " +
                        location.getLongitude(), illegalArgumentException);
            }

            return resultMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            mOrderFragment.setLocation(s);
            super.onPostExecute(s);
        }
    }
}
