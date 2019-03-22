package com.example.thecoffeehouse.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.news.NewsFragment;
import com.example.thecoffeehouse.order.OrderFragment;
import com.example.thecoffeehouse.profile.ProfileFragment;
import com.example.thecoffeehouse.store.views.StoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    private BottomNavigationView navigation;
    private FragmentManager mFragmentManager;
    private SharedPreferences mPrefs;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        loadFragment(NewsFragment.newInstance());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view) {
        navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getFragmentManager();
        mPrefs = getContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE);

    }

    private void loadFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void checkdataUser() {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        User user = gson.fromJson(json, User.class);
        if (json != null) {
            Toast.makeText(getContext(), "Co roi" + user.getFirstName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Rong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkdataUser();
    }
}
