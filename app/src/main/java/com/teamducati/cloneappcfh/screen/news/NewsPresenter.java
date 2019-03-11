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
    NewsContract.View mListNewsView;
    private ArrayList<NewsPromotion> mAndroidArrayList;
    private CompositeDisposable mCompositeDisposable;
    Disposable disposable;
    RetrofitConfig retrofitConfig;

    public NewsPresenter(NewsContract.View listNewsPromotion) {
        this.mListNewsView = listNewsPromotion;
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
                        mListNewsView.showListNewsPromotion(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mListNewsView.handleError();
                    }

                    @Override
                    public void onComplete() {
                        mListNewsView.handleSuccess();
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
                        mListNewsView.showListNews(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mListNewsView.handleError();
                    }

                    @Override
                    public void onComplete() {
                        mListNewsView.handleSuccess();
                    }
                });
    }

    @Override
    public void start() {

    }
}
