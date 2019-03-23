package com.example.thecoffeehouse.data.model.AppRepositoryNews;

import com.example.thecoffeehouse.data.model.entity.ResponseNews;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface AppRepositoryNews {

    Single<List<ResponseNews>> getListForNewsFromDatabase();

    Single<List<ResponseNews>> getNews();

    Flowable<Long> loadApiForNewsToDatabase();

}
