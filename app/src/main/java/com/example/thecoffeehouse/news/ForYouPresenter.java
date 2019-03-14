package com.example.thecoffeehouse.news;
import com.example.thecoffeehouse.data.RetrofitInstance;
import com.example.thecoffeehouse.data.model.RetrofitInterface;
import com.example.thecoffeehouse.data.model.entity.ResponseForYou;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ForYouPresenter implements ForYouPresenterImp {
    private ForYouView newsforyou;
    public ForYouPresenter(ForYouView forYouView)
    {
        this.newsforyou=forYouView;
    }
    public Observable <List<ResponseForYou>> getObservable() {
        return RetrofitInstance.getInstance().create(RetrofitInterface.class)
                .getForYou()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getForYou() {
        getObservable().subscribeWith(getObserver());
    }


    public DisposableObserver <List<ResponseForYou>> getObserver() {
        return new DisposableObserver <List<ResponseForYou>>() {

            @Override
            public void onNext(@NonNull List<ResponseForYou> listResponse) {
                newsforyou.displayForYou(listResponse);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
               newsforyou.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
