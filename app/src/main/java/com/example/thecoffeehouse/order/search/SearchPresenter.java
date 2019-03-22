package com.example.thecoffeehouse.order.search;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.order.hightlight.HighLightDrinksView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements ISearch {
    private SearchView searchView;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void getProduct() {
        getObservable ().subscribeWith (getObserver ());
    }

    public Single<List<DataItem>> getObservable() {
        return ApiHandler.getInstance ().getAppApi ()
                .getProduct ()
                .subscribeOn (Schedulers.io ())
                .flatMap (apiProduct -> Observable.fromIterable (apiProduct.getData ()))
                .toList ()
                .observeOn (AndroidSchedulers.mainThread ());
    }

    public DisposableSingleObserver<List<DataItem>> getObserver() {
        return new DisposableSingleObserver<List<DataItem>> () {
            @Override
            public void onSuccess(List<DataItem> dataItems) {
                searchView.displayProduct (dataItems);
            }

            @Override
            public void onError(Throwable e) {
                searchView.showToast (e.getMessage ());
            }
        };
    }



}
