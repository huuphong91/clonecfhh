package com.example.thecoffeehouse.store.presenters;

import android.annotation.SuppressLint;

import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.store.views.StoreView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StorePresenterIpm implements StorePresenter {

    private CompositeDisposable compositeDisposable;
    private AppRepository mAppRepository;
    private StoreView mView;

    public StorePresenterIpm(StoreView view) {
        mAppRepository = new AppRespositoryImp(view.getActivity().getApplication());
        mView = view;
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadListStore() {
        Disposable disposable = mAppRepository.getListStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::onStoreLoaded,mView::onError);
        compositeDisposable.add(disposable);
        //Call from SplashScreen
        //mAppRepository.loadApiToDatabase();
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadListStoreFromDatabase() {
        Disposable disposable = mAppRepository.getListStoreFromDatabase().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::onStoreLoaded,mView::onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onStop() {
        compositeDisposable.dispose();
    }
}
