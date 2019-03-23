package com.example.thecoffeehouse.data.model.AppRepositoryNews;

import android.app.Application;

import com.example.thecoffeehouse.data.ApiHandler;
import com.example.thecoffeehouse.data.model.entity.*;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AppRespositoryImpNewsPromotion implements AppRepositoryNewsPromotion {

    private ForYouDao newsForYouDao;

    public AppRespositoryImpNewsPromotion(Application app) {
        ForYouDatabase database = ForYouDatabase.getDatabase(app);
        this.newsForYouDao = database.forYouDao();
    }

    @Override
    public Single<List<ResponseForYou>> getListNewsFromDatabase() {
        return newsForYouDao.getForYou();
    }

    @Override
    public Single<List<ResponseForYou>> getForYou() {
        return ApiHandler.getInstance().getAppApi().getForYou();
    }


    @Override
    public Flowable<Long> loadApiNewsToDatabase() {
        return  getForYou().toFlowable()
                .subscribeOn(Schedulers.io())
                .flatMap(responseForYou -> {
                    newsForYouDao.deleteAll();
                    return Flowable.fromIterable(responseForYou);
                })
                .flatMap(responseForYou -> Flowable.fromCallable(() -> newsForYouDao.insertForYouNews(responseForYou)));
    }
}