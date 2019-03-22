package com.example.thecoffeehouse.order.drinks;


import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DrinksPresenter implements DrinksPresenterImp {

    private DrinksView drinksView;

    public DrinksPresenter(DrinksView drinksView) {
        this.drinksView = drinksView;
    }

    @Override
    public void getProduct() {
        getObservable().subscribeWith(getObserver());
    }

    public Single<List<DataItem>> getObservable() {
        return ApiHandler.getInstance().getAppApi()
                .getProduct()
                .subscribeOn(Schedulers.io())
                .flatMap(order -> Observable.fromIterable(order.getData()))
                .filter (dataItem -> dataItem.getCategId ().get (0) != 9)
                .filter (dataItem -> dataItem.getVariants ().get (0).getVal () != null)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableSingleObserver<List<DataItem>> getObserver() {
        return new DisposableSingleObserver<List<DataItem>>() {


            @Override
            public void onSuccess(List<DataItem> dataItems) {
                drinksView.displayProduct(dataItems);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                drinksView.displayError("Error fetching Movie Data");
            }

        };
    }
}
