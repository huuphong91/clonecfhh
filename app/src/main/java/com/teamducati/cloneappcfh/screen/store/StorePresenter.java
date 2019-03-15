package com.teamducati.cloneappcfh.screen.store;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import java.util.List;
import androidx.core.content.ContextCompat;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StorePresenter implements StoreContract.Presenter {

    private StoreContract.View mStoreView;
    private RetrofitFactory mRetrofitFactory;

    public StorePresenter(StoreContract.View storeView) {
        this.mStoreView = storeView;
        mStoreView.setPresenter(this);
        mRetrofitFactory = new RetrofitFactory();
    }

    @Override
    public BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
    public void start() {

    }
}
