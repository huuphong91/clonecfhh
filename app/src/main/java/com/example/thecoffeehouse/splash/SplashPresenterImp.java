package com.example.thecoffeehouse.splash;

import android.app.Application;

import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashPresenterImp implements SplashPresenter {
    private Application application;
    private AppRepository repository;
    private SplashView view;


    public SplashPresenterImp(Application application, SplashView view) {
        this.application = application;
        this.view = view;
        repository = new AppRespositoryImp(application);
    }

    @Override
    public void loadStore() {
        repository.loadApiToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {});
    }
}
