package com.example.thecoffeehouse.data;

import android.annotation.SuppressLint;
import android.app.Application;

import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreDao;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AppRespositoryImp implements AppRepository {

    private StoreDao storeDao;

    public AppRespositoryImp(Application app) {
        StoreDatabase database = StoreDatabase.getDatabase(app);
        this.storeDao = database.storeDao();
    }

    @Override
    public Observable<Order> getCartItem() {
        return ApiHandler.getInstance ().getAppApi ().getProduct ();
    }
    @Override
    public Single<StoreResponeObject> getListStore() {
        return ApiHandler.getInstance().getAppApi().getListStore();
    }

    @Override
    public Observable<List<Category>> getCategory() {
        return ApiHandler.getInstance().getAppApi().getCategory();
    }


    @Override
    public Single<List<Store>> getListStoreFromDatabase() {
        return storeDao.getListStore();
    }

    @SuppressLint("CheckResult")
    @Override
    public Flowable<List<Long>> loadApiToDatabase() {
        return getListStore().toFlowable()
                .subscribeOn(Schedulers.io())
                .flatMap(storeResponeObject -> {
                    storeDao.deleteAll();
                    return Flowable.fromIterable(storeResponeObject.listState);
                })
                .flatMap(state -> Flowable.fromIterable(state.districts))
                .flatMap(district -> Flowable.fromCallable(() -> storeDao.insertStores(district.stores)));


//        return getListStore().subscribeOn(Schedulers.io())
//                .toObservable()
//                .flatMap(new Function<StoreResponeObject, ObservableSource<StoreResponeObject.State>>() {
//                    @Override
//                    public ObservableSource<StoreResponeObject.State> apply(StoreResponeObject storeResponeObject) throws Exception {
//                        storeDao.deleteAll();
//                        return Observable.fromIterable(storeResponeObject.listState);
//                    }
//                }).flatMap(state -> Observable.fromIterable(state.districts))
//                .flatMap(district -> Observable.fromIterable(district.stores))
//                .flatMap(new Function<Store, ObservableSource<Long>>() {
//                    @Override
//                    public ObservableSource<Long> apply(Store store) throws Exception {
//                        long result = storeDao.insertStore(store);
//                        return Observable.just(result);
//                    }
//                }).toFlowable(BackpressureStrategy.BUFFER);




//                .flatMap(state -> {
//
//                     Observable.fromIterable(state.districts);
//                })
//                .flatMap(storeResponeObject -> Observable.fromIterable(storeResponeObject.listState))
//                .flatMap(state -> Observable.fromIterable(state.districts))
//                .flatMap(district -> Observable.fromIterable(district.stores))
//                .toFlowable(null);


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
    }

    @Override
    public Observable<Order> getProduct() {
        return ApiHandler.getInstance().getAppApi().getProduct();
    }
}
