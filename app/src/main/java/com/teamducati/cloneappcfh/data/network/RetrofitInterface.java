package com.teamducati.cloneappcfh.data.network;

import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("news_promotion/")
    Observable<List<NewsPromotion>> getAllNewsPromotion();

    @GET("news/")
    Observable<List<News>> getAllNews();
}
