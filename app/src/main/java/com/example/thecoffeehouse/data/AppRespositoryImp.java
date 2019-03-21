package com.example.thecoffeehouse.data;

import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreDao;
import com.example.thecoffeehouse.data.model.store.StoreDatabase;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AppRespositoryImp implements AppRepository {

    private StoreDao storeDao;

    public AppRespositoryImp(Application app) {
        StoreDatabase database = StoreDatabase.getDatabase(app);
        this.storeDao = database.storeDao();
    }

    @Override
    public Single<StoreResponeObject> getListStore() {
        return ApiHandler.getInstance ().getAppApi ().getListStore ();
    }

    @Override
    public Observable<Order> getCartItem() {
        return ApiHandler.getInstance ().getAppApi ().getProduct ();
    }

    @Override
    public Observable<List<Category>> getCategory() {
        return ApiHandler.getInstance ().getAppApi ().getCategory ();
    }


    @Override
    public Single<List<Store>> getListStoreFromDatabase() {
        return storeDao.getListStore();
    }

    @SuppressLint("CheckResult")
    @Override
    public Flowable loadApiToDatabase() {
//        getListStore().subscribeOn(Schedulers.io())
//                .subscribe(storeResponeObject -> {
//                    storeDao.deleteAll();
//                    Observable.just(storeResponeObject)
//                            .flatMap(storeResponeObject2 -> Observable.fromIterable(storeResponeObject2.listState))
//                            .flatMap(state -> Observable.fromIterable(state.districts))
//                            .flatMap(district -> Observable.fromIterable(district.stores))
//                            .subscribe(store -> {
//                                Log.d("MY_TAG", "loadApiToDatabase: " + storeDao.insertStore(store));
//                            }, throwable -> {
//                                Log.d("MY_TAG", "loadApiToDatabase: ERROR");
//                            }, () -> {
//                                Log.d("MY_TAG", "loadApiToDatabase: COMPLETED");
//                                //return Single.just(new ArrayList<String>());
//                            });
////                            .subscribe(()->{},store -> {
////                                Log.d("MY_TAG", "loadApiToDatabase: "+storeDao.insertStore(store));
////                            });
////                },throwable -> {});
//                });
        return getListStore().subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(new Function<StoreResponeObject, ObservableSource<StoreResponeObject.State>>() {
                    @Override
                    public ObservableSource<StoreResponeObject.State> apply(StoreResponeObject storeResponeObject) throws Exception {
                        storeDao.deleteAll();
                        return Observable.fromIterable(storeResponeObject.listState);
                    }
                }).flatMap(state -> Observable.fromIterable(state.districts))
                .flatMap(district -> Observable.fromIterable(district.stores))
                .flatMap(new Function<Store, ObservableSource<Store>>() {
                    @Override
                    public ObservableSource<Store> apply(Store store) throws Exception {
                        storeDao.insertStore(store);
                        return null;
                    }
                }).toFlowable(null);
//                .flatMap(state -> {
//
//                     Observable.fromIterable(state.districts);
//                })
//                .flatMap(storeResponeObject -> Observable.fromIterable(storeResponeObject.listState))
//                .flatMap(state -> Observable.fromIterable(state.districts))
//                .flatMap(district -> Observable.fromIterable(district.stores))
//                .toFlowable(null);
    }
}
