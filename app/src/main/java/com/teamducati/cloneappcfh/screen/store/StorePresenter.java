package com.teamducati.cloneappcfh.screen.store;

import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.APIStoreMap.APIStore;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StatesItem;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StorePresenter implements StoreContract.Presenter {

    @Nullable
    private StoreContract.View mStoreView;

    private RetrofitFactory mRetrofitFactory;

    @Inject
    public StorePresenter() {
        mRetrofitFactory = new RetrofitFactory();
    }

    @Override
    public void onGetAllStore() {
        mRetrofitFactory.getInstanceRetrofitInterface().getAllStoreMap()
                .subscribeOn(Schedulers.io())
                .flatMap(apiStore -> Observable.fromIterable(apiStore.getStates()))
                .flatMap(statesItem -> Observable.fromIterable(statesItem.getDistricts()))
                .flatMap(districtsItem -> Observable.fromIterable(districtsItem.getStores()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StoresItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StoresItem> value) {
                        mStoreView.showListStore(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onGetAllProvince() {
        mRetrofitFactory.getInstanceRetrofitInterface().getAllStoreMap()
                .subscribeOn(Schedulers.io())
                .flatMap(apiStore -> Observable.fromIterable(apiStore.getStates()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StatesItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StatesItem> statesItems) {
                        mStoreView.showListProvince(statesItems);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void takeView(StoreContract.View view) {
        mStoreView = view;
    }

    @Override
    public void dropView() {
        mStoreView = null;
    }
}
