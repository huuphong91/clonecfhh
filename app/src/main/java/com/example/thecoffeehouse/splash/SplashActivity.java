package com.example.thecoffeehouse.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

    final String TAG = "SplashActivity";
    private SplashPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);
        presenter = new SplashPresenterImp (getApplication (), this);
        presenter.loadStore ();
    }

    @Override
    public void onLoadStoreSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }
}
