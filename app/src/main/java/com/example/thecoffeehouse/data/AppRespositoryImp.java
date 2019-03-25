package com.example.thecoffeehouse.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.example.thecoffeehouse.data.model.entity.ForYouDao;
import com.example.thecoffeehouse.data.model.entity.ForYouDatabase;
import com.example.thecoffeehouse.data.model.entity.NewsDataDao;
import com.example.thecoffeehouse.data.model.entity.NewsDatabase;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.data.model.notification.Notification;
import com.example.thecoffeehouse.data.model.notification.NotificationDAO;
import com.example.thecoffeehouse.data.model.notification.NotificationDatabase;
import com.example.thecoffeehouse.data.model.product.Category;
import com.example.thecoffeehouse.data.model.product.Order;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreDao;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AppRespositoryImp implements AppRepository {

    private StoreDao storeDao;
    private ForYouDao forYouDao;
    private NewsDataDao newsDao;
    private NotificationDAO notificationDAO;

    public AppRespositoryImp(Application app) {
        StoreDatabase database = StoreDatabase.getDatabase(app);
        NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(app);
        this.storeDao = database.storeDao();
        NewsDatabase databasenews = NewsDatabase.getDatabase(app);
        this.newsDao = databasenews.newsDao();
        ForYouDatabase databaseForYou=ForYouDatabase.getDatabase(app);
        this.forYouDao=databaseForYou.forYouDao();
        this.notificationDAO = notificationDatabase.notificationDAO();

    }

    @Override
    public Observable<Order> getCartItem() {
        return ApiHandler.getInstance().getAppApi().getProduct();
    }

    @Override
    public Single<List<ResponseNews>> getListForNewsFromDatabase() {
        return newsDao.getNews();
    }

    @Override
    public Single<List<ResponseNews>> getNews() {
        return ApiHandler.getInstance().getAppApi().getNews();
    }

    @Override
    public Flowable<Long> loadApiForNewsToDatabase() {
        return  getNews().toFlowable()
                .subscribeOn(Schedulers.io())
                .flatMap(responseNews-> {
                    newsDao.deleteAll();
                    return Flowable.fromIterable(responseNews);
                })
                .flatMap(responseNews -> Flowable.fromCallable(() -> newsDao.insertNews(responseNews)));
    }

    @Override
    public Single<List<ResponseForYou>> getListNewsFromDatabase() {
        return forYouDao.getForYou();
    }

    @Override
    public Single<List<ResponseForYou>> getForYou() {
        return ApiHandler.getInstance().getAppApi().getForYou();
    }

    @Override
    public Flowable<Long> loadApiNewsToDatabase() {
        return getForYou ().toFlowable ()
                .subscribeOn (Schedulers.io ())
                .flatMap (responseForYou -> {
                    forYouDao.deleteAll ();
                    return Flowable.fromIterable (responseForYou);
                })
                .flatMap (responseForYou -> Flowable.fromCallable (() -> forYouDao.insertForYouNews (responseForYou)));
    }
    @Override
    public void insertNotification(Notification notification) {
        Single.fromCallable(() -> notificationDAO.insertNotification(notification))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aLong, throwable) -> {
                    //
                    Log.d("insertNotification: ", aLong + "--" + throwable);
                });
    }

    public LiveData<List<Notification>> getNotification(LifecycleOwner lifecycleOwner) {
//        notificationDAO.getListNotification().observe(lifecycleOwner, notifications ->{
//            Log.d("getNotification: ", notifications.toString());
//        });
        return notificationDAO.getListNotification();

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
