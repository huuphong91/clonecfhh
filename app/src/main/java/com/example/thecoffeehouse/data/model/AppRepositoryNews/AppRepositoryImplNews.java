package com.example.thecoffeehouse.data.model.AppRepositoryNews;

import android.app.Application;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.entity.*;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AppRepositoryImplNews implements AppRepositoryNews {

    private NewsDataDao newsDao;
    public AppRepositoryImplNews(Application app) {
        NewsDatabase database = NewsDatabase.getDatabase(app);
        this.newsDao = database.newsDao();
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
}
