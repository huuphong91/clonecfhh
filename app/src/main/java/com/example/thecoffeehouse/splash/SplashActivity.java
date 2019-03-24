package com.example.thecoffeehouse.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;

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
        setAnimation ();
        presenter = new SplashPresenterImp (getApplication (), this);
        presenter.loadStore ();
        presenter.loadNews();
        presenter.loadpromotionNews();
    }

    @Override
    public void onLoadStoreSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        startActivity ();
//        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }

    @Override
    public void onLoadNewsSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        startActivity ();
//        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }

    @Override
    public void OnLoadNewsPromotionSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        startActivity ();
//        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }

    @Override
    public void OnError(Throwable throwable) {
        startActivity();
        finish();
    }

    private void startActivity() {
        Intent i = new Intent (SplashActivity.this, MainActivity.class);
        if (Build.VERSION.SDK_INT > 20) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation (SplashActivity.this);
            startActivity (i, options.toBundle ());
        } else {
            startActivity (i);
        }
    }

    private void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide ();
            slide.setSlideEdge (Gravity.LEFT);
            slide.setDuration (400);
            slide.setInterpolator (new AccelerateDecelerateInterpolator ());
            getWindow ().setExitTransition (slide);
            getWindow ().setEnterTransition (slide);
        }
    }
}
