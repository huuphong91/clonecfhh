package com.example.thecoffeehouse.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

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
    }

    @Override
    public void onLoadStoreSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        startActivity ();
//        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }

    @Override
    public void onLoadError(Throwable throwable) {
        Log.e(TAG, "onLoadError: "+throwable.getLocalizedMessage());
        startActivity ();
//        startActivity (new Intent (SplashActivity.this, MainActivity.class));
        finish ();
    }

    private void startActivity() {
        Intent i = new Intent (SplashActivity.this, MainActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation (SplashActivity.this);
        startActivity (i, options.toBundle ());
    }

    private void setAnimation() {
        Slide slide = new Slide ();
        slide.setSlideEdge (Gravity.LEFT);
        slide.setDuration (400);
        slide.setInterpolator (new AccelerateDecelerateInterpolator ());
        getWindow ().setExitTransition (slide);
        getWindow ().setEnterTransition (slide);
    }
}
