package com.example.thecoffeehouse.order.hightlight;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HighLightPresenter implements IHighLightDrinks {
    private HighLightDrinksView highLightView;

    public HighLightPresenter(HighLightDrinksView highLightView) {
        this.highLightView = highLightView;
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
                .filter (dataItem -> dataItem.getCategId ().get (0) == 9)
                .filter (dataItem -> dataItem.getVariants ().get (0).getVal () != null)
                .toList ()
                .observeOn (AndroidSchedulers.mainThread ());
    }

    public DisposableSingleObserver<List<DataItem>> getObserver() {
        return new DisposableSingleObserver<List<DataItem>> () {
            @Override
            public void onSuccess(List<DataItem> dataItems) {
                highLightView.displayProduct (dataItems);
            }

            @Override
            public void onError(Throwable e) {
                highLightView.showToast (e.getMessage ());
            }
        };
    }


}
