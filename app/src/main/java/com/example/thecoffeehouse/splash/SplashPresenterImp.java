package com.example.thecoffeehouse.splash;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SplashPresenterImp implements SplashPresenter {
    private AppRepository repository;
    private SplashView view;


    SplashPresenterImp(Application application, SplashView view) {
        this.view = view;
        repository = new AppRespositoryImp(application);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadStore() {
        repository.loadApiToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(longs -> {
                    Log.d(TAG, "loadStore: added" + longs);
                }, view::OnError, view::onLoadStoreSuccess);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNews() {
        repository.loadApiForNewsToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(longs -> {
                    Log.d(TAG, "loadNews: added" + longs);
                }, view::OnError, view::onLoadNewsSuccess);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadpromotionNews() {
        repository.loadApiNewsToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(longs -> {
                    Log.d(TAG, "promotionNews: added" + longs);
                }, view::OnError, view::OnLoadNewsPromotionSuccess);
    }
}
