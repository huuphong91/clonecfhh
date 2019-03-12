package com.teamducati.cloneappcfh.screen.news;

import com.teamducati.cloneappcfh.data.network.RetrofitConfig;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter implements NewsContract.Presenter {
    NewsContract.View mNewsView;
    private CompositeDisposable mCompositeDisposable;
    Disposable disposable;
    RetrofitConfig retrofitConfig;

    public NewsPresenter(NewsContract.View newsView) {
        this.mNewsView = newsView;
        mNewsView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        retrofitConfig = new RetrofitConfig();
    }

    @Override
    public void getAllListNewsPromotion() {
        retrofitConfig.getInstanceRetrofit().getAllNewsPromotion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<NewsPromotion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsPromotion> value) {
                        mNewsView.showListNewsPromotion(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.handleError();
                    }

                    @Override
                    public void onComplete() {
                        mNewsView.handleSuccess();
                    }
                });

    }

    @Override
    public void getAllListNews() {
        retrofitConfig.getInstanceRetrofit().getAllNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<News> value) {
                        mNewsView.showListNews(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.handleError();
                    }

                    @Override
                    public void onComplete() {
                        mNewsView.handleSuccess();
                    }
                });
    }


    private NewsContract.View mNewsView;

    public NewsPresenter(NewsContract.View newsView) {
        this.mNewsView = newsView;
        mNewsView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
