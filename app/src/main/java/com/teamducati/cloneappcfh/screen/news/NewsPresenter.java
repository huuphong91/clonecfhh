package com.teamducati.cloneappcfh.screen.news;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.teamducati.cloneappcfh.data.network.RetrofitFactory;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unchecked")
public class NewsPresenter extends FirebaseMessagingService implements NewsContract.Presenter {

    @Nullable
    private NewsContract.View mNewsView;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public NewsPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAllListNewsPromotion() {
        RetrofitFactory.getInstanceRetrofitInterface().getAllNewsPromotion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsPromotion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<NewsPromotion> value) {
                        if (mNewsView != null) {
                            mNewsView.getListNewsPromotion(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mNewsView != null) {
                            mNewsView.getHandleError();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mNewsView != null) {
                            mNewsView.getHandleSuccess();
                        }
                    }
                });
    }

    @Override
    public void onAllListNews() {
        RetrofitFactory.getInstanceRetrofitInterface().getAllNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<News> value) {
                        if (mNewsView != null) {
                            mNewsView.getListNews(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mNewsView != null) {
                            mNewsView.getHandleError();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mNewsView != null) {
                            mNewsView.getHandleSuccess();
                        }
                    }
                });
    }

    @Override
    public void takeView(NewsContract.View view) {
        mNewsView = view;
        onAllListNews();
        onAllListNewsPromotion();
    }

    @Override
    public void dropView() {
        mNewsView = null;
        mCompositeDisposable.clear();
    }
}
