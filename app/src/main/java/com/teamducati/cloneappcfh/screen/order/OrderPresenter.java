package com.teamducati.cloneappcfh.screen.order;

import android.util.Log;

import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderPresenter implements OrderContract.Presenter {

    @Nullable
    OrderContract.View mOrderView;

    @Inject
    public OrderPresenter() {

    }

//    @Override
//    public void start() {
//        Log.d(OrderFragment.TAG, "start: ");
//        onGetAllProductPresenter();
//    }

    @Override
    public void onGetAllProductPresenter() {
        RetrofitFactory.getInstanceRetrofitInterface().getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemProductResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(OrderFragment.TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ItemProductResponse value) {
                        Log.d(OrderFragment.TAG, "onNext: " + value.toString());
                        mOrderView.onGetAllProductView(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(OrderFragment.TAG, "onError: " + e.toString());
                        e.printStackTrace();
                        mOrderView.displayError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(OrderFragment.TAG, "onComplete: ");
                    }
                });
    }

    @Override
    public void takeView(OrderContract.View view) {
        mOrderView = view;
    }

    @Override
    public void dropView() {
        mOrderView = null;
    }
}
