package com.teamducati.cloneappcfh.data.network;

import com.teamducati.cloneappcfh.entity.APIStoreMap.APIStore;
import com.teamducati.cloneappcfh.entity.News;
import com.teamducati.cloneappcfh.entity.NewsPromotion;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("v2/news_promotion/")
    Observable<List<NewsPromotion>> getAllNewsPromotion();

    @GET("v2/news/")
    Observable<List<News>> getAllNews();

    @GET("get_list_store")
    Observable<APIStore> getAllStoreMap();
}
