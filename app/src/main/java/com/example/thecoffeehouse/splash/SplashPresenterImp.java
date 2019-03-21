package com.example.thecoffeehouse.splash;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashPresenterImp implements SplashPresenter {
    private AppRepository repository;
    private SplashView view;


    public SplashPresenterImp(Application application, SplashView view) {
        this.view = view;
        repository = new AppRespositoryImp(application);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadStore() {
        repository.loadApiToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {},throwable -> {},view::onLoadStoreSuccess);
    }
}
