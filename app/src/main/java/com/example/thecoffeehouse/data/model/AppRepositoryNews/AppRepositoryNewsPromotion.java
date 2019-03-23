package com.example.thecoffeehouse.data.model.AppRepositoryNews;

import com.example.thecoffeehouse.data.model.entity.ResponseForYou;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface AppRepositoryNewsPromotion {

    Single<List<ResponseForYou>> getListNewsFromDatabase();

    Single<List<ResponseForYou>> getForYou();

    Flowable<Long> loadApiNewsToDatabase();


}
