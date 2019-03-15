package com.teamducati.cloneappcfh.screen.order;

import android.util.Log;

import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderPresenter implements OrderContract.Presenter {

    private OrderContract.View mOrderView;

    public OrderPresenter(OrderContract.View orderView) {
        this.mOrderView = orderView;
        mOrderView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(OrderFragment.TAG, "start: ");
        onGetAllProductPresenter();
    }

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
}
