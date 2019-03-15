package com.teamducati.cloneappcfh.screen.news;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter extends FirebaseMessagingService implements NewsContract.Presenter  {
    private NewsContract.View mNewsView;
    private CompositeDisposable mCompositeDisposable;
    private Disposable disposable;
    private RetrofitFactory retrofitFactory;
    public NewsPresenter(NewsContract.View newsView) {
        this.mNewsView = newsView;
        mNewsView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        retrofitFactory = new RetrofitFactory();

    }

    @Override
    public void onAllListNewsPromotion() {
        retrofitFactory.getInstanceRetrofitInterface().getAllNewsPromotion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsPromotion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsPromotion> value) {
                        mNewsView.getListNewsPromotion(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.getHandleError();
                    }

                    @Override
                    public void onComplete() {
                        mNewsView.getHandleSuccess();
                    }
                });

    }

    @Override
    public void onAllListNews() {
        retrofitFactory.getInstanceRetrofitInterface().getAllNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<News> value) {
                        mNewsView.getListNews(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.getHandleError();
                    }

                    @Override
                    public void onComplete() {
                        mNewsView.getHandleSuccess();
                    }
                });
    }

    @Override
    public void start() {
        onAllListNews();
        onAllListNewsPromotion();
    }
}
