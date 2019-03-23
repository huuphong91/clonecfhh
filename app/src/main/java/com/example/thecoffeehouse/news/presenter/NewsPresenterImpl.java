package com.example.thecoffeehouse.news.presenter;

import android.annotation.SuppressLint;

import com.example.thecoffeehouse.data.model.AppRepositoryNews.AppRepositoryImplNews;
import com.example.thecoffeehouse.news.viewnews.NewsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenterImpl implements NewsPresenter {
    private  NewsView newsView;
    private CompositeDisposable compositeDisposableNews;
    private AppRepositoryImplNews mAppRepositoryImplNews;
    public NewsPresenterImpl(NewsView newsView) {

        mAppRepositoryImplNews=new AppRepositoryImplNews(newsView.getActivity().getApplication());
        this.newsView =newsView;
        compositeDisposableNews = new CompositeDisposable();
    }

    @Override
    public void getNews()
    {
        Disposable disposable = mAppRepositoryImplNews.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsView::displayNews);
                 compositeDisposableNews.add(disposable);
    }

    @Override
    public void loadNewsForFromDatabase() {
        Disposable disposable = mAppRepositoryImplNews.getListForNewsFromDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsView::displayNews);
      compositeDisposableNews.add(disposable);
        mAppRepositoryImplNews.loadApiForNewsToDatabase().observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {},throwable -> {});
    }


    @SuppressLint("CheckResult")


    @Override
    public void onDestroy() {
        compositeDisposableNews.clear();
    }

    @Override
    public void onStop() {
        compositeDisposableNews.dispose();
    }


}
