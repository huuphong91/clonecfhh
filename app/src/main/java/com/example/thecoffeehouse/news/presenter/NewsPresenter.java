package com.example.thecoffeehouse.news.presenter;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import com.example.thecoffeehouse.news.viewnews.NewsView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter implements NewsPresenterImp {
    private NewsView newsview;

    public NewsPresenter(NewsView newsView) {
        this.newsview = newsView;
    }

    public Observable<List<ResponseNews>> getObservable() {
        return ApiHandler.getInstance().getAppApi()
                .getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getNews() {
        getObservable().subscribeWith(getObserver());
    }


    public DisposableObserver<List<ResponseNews>> getObserver() {
        return new DisposableObserver<List<ResponseNews>>() {

            @Override
            public void onNext(@NonNull List<ResponseNews> listResponse) {
                newsview.displayNews(listResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                newsview.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
