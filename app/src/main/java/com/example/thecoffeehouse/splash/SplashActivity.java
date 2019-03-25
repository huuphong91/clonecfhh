package com.example.thecoffeehouse.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.MainActivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;

public class SplashActivity extends AppCompatActivity implements SplashView {

    final String TAG = "SplashActivity";
    private SplashPresenter presenter;
    private boolean isStoreLoaded = false, isNewsLoaded = false, isPromotionLoaded = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);
        setAnimation ();
        presenter = new SplashPresenterImp (getApplication (), this);
        Completable.complete ()
                .delay (3000, TimeUnit.MILLISECONDS)
                .doOnComplete (() -> {
                    presenter.loadStore ();
                    presenter.loadNews ();
                    presenter.loadpromotionNews ();

                })
                .subscribe ();
    }

    @Override
    public void onLoadStoreSuccess() {
        Log.d (TAG, "onLoadStoreSuccess: ");
        isStoreLoaded = true;
        checkLoadResult ();
    }

    @Override
    public void onLoadNewsSuccess() {
        Log.d (TAG, "onLoadNewsSuccess: ");
        isNewsLoaded = true;
        checkLoadResult ();
    }

    @Override
    public void OnLoadNewsPromotionSuccess() {
        Log.d (TAG, "OnLoadNewsPromotionSuccess: ");
        isPromotionLoaded = true;
        checkLoadResult ();
    }

    @Override
    public void OnError(Throwable throwable) {
        Log.e (TAG, "OnError: " + throwable.getLocalizedMessage ());
        Toast.makeText (this, throwable.getLocalizedMessage (), Toast.LENGTH_SHORT).show ();
        startActivity ();
        finish ();
    }

    private void startActivity() {
        Intent i = new Intent (SplashActivity.this, MainActivity.class);
        i.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation (SplashActivity.this);
        startActivity (i, options.toBundle ());
    }

    private void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Explode explode = new Explode ();
            explode.setDuration (700);
            explode.setInterpolator (new AccelerateDecelerateInterpolator ());
            getWindow ().setExitTransition (explode);
            getWindow ().setEnterTransition (explode);
        }
    }

    private void checkLoadResult() {
        if (isNewsLoaded && isStoreLoaded && isPromotionLoaded) {
            startActivity ();
            finish ();
        }
    }

}
